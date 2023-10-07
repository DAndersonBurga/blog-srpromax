package com.anderson.blogsrpromax.app.controllers;

import com.anderson.blogsrpromax.app.models.dto.PostDTO;
import com.anderson.blogsrpromax.app.services.IUsuarioService;
import com.anderson.blogsrpromax.app.user.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/create-post")
@RequiredArgsConstructor
public class CreatePostController {

    private UserDetails userDetails;
    private final IUsuarioService usuarioService;

    @GetMapping
    public String createPostIndex(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        PostDTO postDTO = new PostDTO();
        this.userDetails = userDetails;

        model.addAttribute("post", postDTO);
        model.addAttribute("user", userDetails);
        return "web/createPost";
    }

    @PostMapping
    public String createPost(@Valid PostDTO postDTO, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("post", postDTO);
            model.addAttribute("user", userDetails);
            return "web/createPost";
        }

        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername()).get();
        usuarioService.crearPost(usuario, postDTO);

        return "redirect:/home";
    }

}
