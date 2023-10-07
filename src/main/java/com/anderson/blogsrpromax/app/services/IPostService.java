package com.anderson.blogsrpromax.app.services;

import com.anderson.blogsrpromax.app.models.entity.Post;

import java.util.List;

public interface IPostService {
    List<Post> findAll();
    List<Post> ultimosPost();
}
