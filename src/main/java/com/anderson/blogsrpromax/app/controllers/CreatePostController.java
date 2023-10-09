package com.anderson.blogsrpromax.app.controllers;

import com.anderson.blogsrpromax.app.models.dto.PostDTO;
import com.anderson.blogsrpromax.app.services.IUsuarioService;
import com.anderson.blogsrpromax.app.services.RecaptchaService;
import com.anderson.blogsrpromax.app.user.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Collection;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/create-post")
@RequiredArgsConstructor
public class CreatePostController {

    private UserDetails userDetails;
    private final IUsuarioService usuarioService;
    private final RecaptchaService recaptchaService;

    @GetMapping
    public String createPostIndex(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        PostDTO postDTO = new PostDTO();
        this.userDetails = userDetails;

        model.addAttribute("tipo","Crear Post");
        model.addAttribute("post", postDTO);
        model.addAttribute("user", userDetails);
        return "web/createPost";
    }

    @PostMapping
    public String createPost(@ModelAttribute(name = "post") @Valid PostDTO postDTO,
                             BindingResult result, @RequestParam(name = "g-recaptcha-response") String captcha, Model model) {

        boolean captchaValid = recaptchaService.validateRecaptcha(captcha);
        model.addAttribute("tipo","Crear Post");

        if (result.hasErrors()) {
            model.addAttribute("post", postDTO);
            model.addAttribute("user", userDetails);
            return "web/createPost";
        }

        if (!captchaValid) {
            model.addAttribute("post", postDTO);
            model.addAttribute("user", userDetails);
            model.addAttribute("error", "Captcha inválido.");
            return "web/createPost";
        }

        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername()).get();
        usuarioService.crearPost(usuario, postDTO);

        return "redirect:/home";
    }

}
