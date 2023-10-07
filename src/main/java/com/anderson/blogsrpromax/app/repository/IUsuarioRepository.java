package com.anderson.blogsrpromax.app.repository;

import com.anderson.blogsrpromax.app.user.Usuario;

import java.util.Optional;

public interface IUsuarioRepository {
    Optional<Usuario> findByEmail(String email);
    Usuario save(Usuario usuario);
}
