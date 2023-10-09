package com.anderson.blogsrpromax.app.controllers;

import com.anderson.blogsrpromax.app.models.entity.Post;
import com.anderson.blogsrpromax.app.services.IPostService;
import com.anderson.blogsrpromax.app.services.IUsuarioService;
import com.anderson.blogsrpromax.app.user.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final IPostService postService;
    private final IUsuarioService usuarioService;

    @GetMapping
    public String home(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "search", required = false) String search, Model model) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails);
        }

        model.addAttribute("pagHome", true);
        List<Post> ultimosPosts = postService.ultimosPost();


        if (search != null) {
            List<Post> postsEncontrados = postService.buscarPosts(search);

            model.addAttribute("posts", postsEncontrados);
            model.addAttribute("ultimosPosts", ultimosPosts);
            return "home";
        }

        List<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);
        model.addAttribute("ultimosPosts", ultimosPosts);
        return "home";
    }

    @GetMapping("/myposts")
    public String myposts(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "search", required = false) String search, Model model) {
        if (userDetails == null) {
            return "home";
        }

        Optional<Usuario> usuario = usuarioService.findByEmail(userDetails.getUsername());
        if (usuario.isPresent()) {

            if (search != null) {
                List<Post> postsEncontrados = postService.buscarPosts(search, usuario.get().getId());
                model.addAttribute("posts", postsEncontrados);
                return "home";
            }

            List<Post> posts = postService.findByUsuarioId(usuario.get().getId());
            model.addAttribute("posts", posts);
        }

        model.addAttribute("user", userDetails);
        return "home";
    }

}
