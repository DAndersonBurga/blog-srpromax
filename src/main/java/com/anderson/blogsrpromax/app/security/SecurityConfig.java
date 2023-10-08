package com.anderson.blogsrpromax.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(AbstractHttpConfigurer::disable) // Deshabilitar los Cross Origin Requests Sharing (CORS)
                .csrf(AbstractHttpConfigurer::disable) // Deshabilitar los Cross Site Request Forgery (CSRF)
                .authorizeHttpRequests(authHttpRequest -> {
                    authHttpRequest.requestMatchers(HttpMethod.GET,"/auth/**").permitAll();
                    authHttpRequest.requestMatchers("/css/**", "/js/**", "/img/**").permitAll();
                    authHttpRequest.requestMatchers(HttpMethod.POST,"/auth/**").permitAll();
                    authHttpRequest.anyRequest().hasAnyAuthority("USER");
                })
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/auth/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .failureUrl("/auth/login?error=true")
                            .defaultSuccessUrl("/home", true)
                            .permitAll();
                })
                .logout(logout -> {
                    logout
                        .logoutSuccessUrl("/auth/logout?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");
                })
                .build();
    }

}
