package com.anderson.blogsrpromax.app.services;

import com.anderson.blogsrpromax.app.models.entity.Post;
import com.anderson.blogsrpromax.app.repository.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    private final IPostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> ultimosPost() {
        return postRepository.ultimosPost();
    }

    @Override
    public List<Post> findByUsuarioId(Long id) {
        return postRepository.findUserPosts(id);
    }

    @Override
    public List<Post> buscarPosts(String search) {
        return postRepository.buscarPosts(search);
    }

    @Override
    public List<Post> buscarPosts(String search, Long id) {
        return postRepository.buscarPosts(search, id);
    }

    @Override
    public void deletePost(Long idPost, Long idUser) {
        postRepository.deletePost(idPost, idUser);
    }

    @Override
    public Optional<Post> findByAuthorAndIdPost(String email, Long idPost) {
        return postRepository.findByAuthorAndIdPost(email, idPost);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }
}
