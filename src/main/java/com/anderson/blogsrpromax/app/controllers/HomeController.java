package com.anderson.blogsrpromax.app.controllers;

import com.anderson.blogsrpromax.app.models.entity.Post;
import com.anderson.blogsrpromax.app.services.IPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final IPostService postService;

    @GetMapping
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            model.addAttribute("user", userDetails);
        }

        List<Post> posts = postService.findAll();
        List<Post> ultimosPosts = postService.ultimosPost();

        model.addAttribute("posts", posts);
        model.addAttribute("ultimosPosts", ultimosPosts);
        return "home";
    }

}
