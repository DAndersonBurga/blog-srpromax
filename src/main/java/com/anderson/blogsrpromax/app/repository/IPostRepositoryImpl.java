package com.anderson.blogsrpromax.app.repository;

import com.anderson.blogsrpromax.app.models.entity.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
            }
        } catch (Exception e) {
            entityManager.persist(post);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findUserPosts(Long id) {
        try {
            return entityManager.createQuery("select p from Post p where p.author.id = :id", Post.class)
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
            return entityManager.createQuery("select p from Post p", Post.class)
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
}
