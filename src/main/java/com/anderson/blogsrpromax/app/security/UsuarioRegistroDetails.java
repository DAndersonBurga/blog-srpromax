package com.anderson.blogsrpromax.app.security;

import com.anderson.blogsrpromax.app.user.Usuario;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioRegistroDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean isEnabled;
    private List<GrantedAuthority> authorities;

    public UsuarioRegistroDetails(Usuario user) {
        this.userName = user.getEmail(); // El email es nuestra imagen o identificador
        this.password = user.getPassword();
        this.isEnabled = user.isEnabled();
        this.authorities = Arrays.stream(user.getRole()
                        .split(",")) // El rol es un string separado por comas
                .map(SimpleGrantedAuthority::new) // Convertimos cada rol en un objeto de tipo GrantedAuthority
                .collect(Collectors.toList()); // Lo convertimos en una lista de GrantedAuthority (permisos)
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
