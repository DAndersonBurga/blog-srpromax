package com.anderson.blogsrpromax.app.services;

import com.anderson.blogsrpromax.app.models.dto.PostDTO;
import com.anderson.blogsrpromax.app.models.entity.Post;
import com.anderson.blogsrpromax.app.register.RegisterRequest;
import com.anderson.blogsrpromax.app.user.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Usuario registrarUsuario(RegisterRequest request);
    Optional<Usuario> findByEmail(String email);
    void guardarTokenVerificacion(Usuario usuario, String token);
    void validarToken(String token);

    void crearPost(Usuario usuario, PostDTO postDTO);
    List<Post> findUserPosts(Long id);
}
