package com.anderson.blogsrpromax.app.services;

import com.anderson.blogsrpromax.app.exceptions.UserAlreadyExistsException;
import com.anderson.blogsrpromax.app.models.dto.PostDTO;
import com.anderson.blogsrpromax.app.models.entity.Post;
import com.anderson.blogsrpromax.app.register.RegisterRequest;
import com.anderson.blogsrpromax.app.register.token.VerificationToken;
import com.anderson.blogsrpromax.app.repository.IPostRepository;
import com.anderson.blogsrpromax.app.repository.IUsuarioRepository;
import com.anderson.blogsrpromax.app.repository.IVerificationTokenRepository;
import com.anderson.blogsrpromax.app.user.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final IVerificationTokenRepository verificationTokenRepository;
    private final IPostRepository postRepository;

    @Override
    public Usuario registrarUsuario(RegisterRequest request) {

        // Verificar que el email no exista en la base de datos
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(request.getEmail());
        if (usuarioOptional.isPresent()) {
            throw new UserAlreadyExistsException("El email " + request.getEmail() + " ya está registrado");
        }

        // Crear el usuario
        Usuario usuario = Usuario.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role("USER")
                .build();

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public void guardarTokenVerificacion(Usuario usuario, String token) {
        // Crear el token de verificación
        VerificationToken verificationToken = VerificationToken.builder()
                .usuario(usuario)
                .token(token)
                .build();

        // Guardar el token de verificación en la base de datos
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void validarToken(String token) {

        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isPresent()) {
            Usuario usuario = verificationToken.get().getUsuario();
            usuario.setEnabled(true); // Habilitar el usuario
            usuarioRepository.save(usuario); // Actualizar el usuario de la base de datos

        }

    }

    @Override
    public void crearPost(Usuario usuario, PostDTO postDTO) {
        Post post = Post.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(usuario)
                .build();

        postRepository.save(post);
    }

}
