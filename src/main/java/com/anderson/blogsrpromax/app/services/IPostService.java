package com.anderson.blogsrpromax.app.services;

import com.anderson.blogsrpromax.app.models.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    List<Post> findAll();
    List<Post> ultimosPost();
    List<Post> findByUsuarioId(Long id);
    List<Post> buscarPosts(String search);
    List<Post> buscarPosts(String search, Long id);
    void deletePost(Long idPost, Long idUser);
    Optional<Post> findByAuthorAndIdPost(String email, Long idPost);
    void save(Post post);
}
