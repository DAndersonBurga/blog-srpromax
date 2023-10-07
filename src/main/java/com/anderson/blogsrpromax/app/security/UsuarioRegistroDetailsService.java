package com.anderson.blogsrpromax.app.security;

import com.anderson.blogsrpromax.app.services.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioRegistroDetailsService implements UserDetailsService {

    private final IUsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioService.findByEmail(email) // Buscamos el usuario por email
                .map(UsuarioRegistroDetails::new) // Convertimos el usuario en un UserDetails
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")); // Si no lo encontramos lanzamos una excepción
    }
}
