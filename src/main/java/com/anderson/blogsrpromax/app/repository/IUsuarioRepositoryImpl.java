package com.anderson.blogsrpromax.app.repository;

import com.anderson.blogsrpromax.app.user.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IUsuarioRepositoryImpl implements IUsuarioRepository {

    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> findByEmail(String email) {
        Optional<Usuario> usuarioOptional = Optional.empty();
        try {
            usuarioOptional = Optional.ofNullable(entityManager.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult());

            return usuarioOptional;
        } catch (NoResultException e) {
            return usuarioOptional;
        }
    }

    @Transactional
    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getId() != null && usuario.getId() > 0) {
            entityManager.merge(usuario);
        } else {
            entityManager.persist(usuario);
        }
        return usuario;
    }
}
