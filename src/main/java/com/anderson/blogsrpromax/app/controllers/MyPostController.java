package com.anderson.blogsrpromax.app.controllers;


import com.anderson.blogsrpromax.app.models.dto.PostDTO;
import com.anderson.blogsrpromax.app.models.entity.Post;
import com.anderson.blogsrpromax.app.services.IPostService;
import com.anderson.blogsrpromax.app.services.IUsuarioService;
import com.anderson.blogsrpromax.app.services.RecaptchaService;
import com.anderson.blogsrpromax.app.user.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home/myposts")
@RequiredArgsConstructor
public class MyPostController {

    private final IPostService postService;
    private final IUsuarioService usuarioService;
    private final RecaptchaService recaptchaService;

    @GetMapping
    public String myposts(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(name = "search", required = false) String search, Model model) {
        if (userDetails == null) {
            return "home";
        }

        Optional<Usuario> usuario = usuarioService.findByEmail(userDetails.getUsername());
        if (usuario.isPresent()) {

            if (search != null) {
                List<Post> postsEncontrados = postService.buscarPosts(search, usuario.get().getId());
                model.addAttribute("posts", postsEncontrados);
                model.addAttribute("user", userDetails);
                return "home";
            }

            List<Post> posts = postService.findByUsuarioId(usuario.get().getId());
            model.addAttribute("posts", posts);
        }

        model.addAttribute("user", userDetails);
        return "home";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("id") Long id, Model model) {

        if (id != null && id > 0) {
            Optional<Post> post = postService.findByAuthorAndIdPost(userDetails.getUsername(),id);
            if (post.isPresent()) {
                model.addAttribute("post", post.get());
                model.addAttribute("tipo", "Actualizar Post");
                model.addAttribute("id", id);
                return "web/createPost";
            } else {
                return "redirect:/home/myposts";
            }
        }

        return "redirect:/home/myposts";
    }

    @PostMapping("/edit")
    public String editarPost(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute(name = "post") @Valid PostDTO postDTO,
                             BindingResult result, @RequestParam(name = "g-recaptcha-response") String captcha, @RequestParam("id") Long id, Model model) {
        System.out.println("Esta dando??");

        if (id == null || id < 0) {
            return "redirect:/home/myposts";
        }

        boolean captchaValid = recaptchaService.validateRecaptcha(captcha);
        model.addAttribute("tipo","Actualizar Post");
        model.addAttribute("id", id);

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

        Optional<Post> post = postService.findByAuthorAndIdPost(userDetails.getUsername(), id);

        if (post.isPresent()) {
            Post postActualizado = post.get();
            postActualizado.setTitle(postDTO.getTitle());
            postActualizado.setContent(postDTO.getContent());
            postService.save(postActualizado);
            return "redirect:/home/myposts";
        }

        return "redirect:/home/myposts";

    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        if (id != null && id > 0) {
            postService.deletePost(id, usuarioService.findByEmail(userDetails.getUsername()).get().getId());
            return "redirect:/home/myposts";
        }

        return "redirect:/home/myposts";
    }
}
