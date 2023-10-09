package com.anderson.blogsrpromax.app.repository;

import com.anderson.blogsrpromax.app.models.entity.Post;

import java.util.List;

public interface IPostRepository {
    void save(Post post);
    List<Post> findUserPosts(Long id);
    List<Post> findAll();
    List<Post> ultimosPost();
    List<Post> buscarPosts(String search);
    List<Post> buscarPosts(String search, Long id);

}
