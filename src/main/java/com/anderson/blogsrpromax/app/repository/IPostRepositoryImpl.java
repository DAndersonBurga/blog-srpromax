package com.anderson.blogsrpromax.app.repository;

import com.anderson.blogsrpromax.app.models.entity.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IPostRepositoryImpl implements IPostRepository {

    private final EntityManager entityManager;

    @Transactional
    @Override
    public void save(Post post) {
        try {
            if (post != null && post.getId() > 0) {
                entityManager.merge(post);
            } else {
                entityManager.persist(post);
            }
        } catch (NullPointerException e) {
            entityManager.persist(post);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findUserPosts(Long id) {
        try {
            return entityManager.createQuery("select p from Post p where p.author.id = :id order by id desc", Post.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findAll() {
        try {
            return entityManager.createQuery("select p from Post p order by id desc", Post.class)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> ultimosPost() {
        try {
            return entityManager.createQuery("SELECT p FROM Post p ORDER BY p.id DESC", Post.class)
                    .setMaxResults(3)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> buscarPosts(String search) {
        try {
            return entityManager.createQuery("SELECT p FROM Post p WHERE p.content like :search OR p.title like :search ORDER BY p.id DESC", Post.class)
                    .setParameter("search", "%" + search + "%")
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> buscarPosts(String search, Long id) {
        try {
            return entityManager.createQuery("SELECT p FROM Post p WHERE (p.content like :search OR p.title like :search) AND p.author.id = :id ORDER BY p.id DESC", Post.class)
                    .setParameter("search", "%" + search + "%")
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Post> findByAuthorAndIdPost(String email, Long idPost) {
        try {
            return Optional.ofNullable(entityManager.createQuery("SELECT p FROM Post p INNER JOIN Usuario u ON p.author.id = u.id WHERE u.email = :email AND p.id = :idPost", Post.class)
                    .setParameter("email", email)
                    .setParameter("idPost", idPost)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public void deletePost(Long idPost, Long idUser) {
        entityManager.createQuery("DELETE FROM Post p WHERE p.id = :idPost AND p.author.id = :idUser")
                .setParameter("idPost", idPost)
                .setParameter("idUser", idUser)
                .executeUpdate();
    }
}
