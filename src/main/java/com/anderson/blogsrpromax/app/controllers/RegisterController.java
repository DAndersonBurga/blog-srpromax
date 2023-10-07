package com.anderson.blogsrpromax.app.controllers;

import com.anderson.blogsrpromax.app.event.UsuarioRegistradoEvent;
import com.anderson.blogsrpromax.app.register.RegisterRequest;
import com.anderson.blogsrpromax.app.register.token.VerificationToken;
import com.anderson.blogsrpromax.app.repository.IVerificationTokenRepository;
import com.anderson.blogsrpromax.app.services.IUsuarioService;
import com.anderson.blogsrpromax.app.user.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final IUsuarioService usuarioService;
    private final ApplicationEventPublisher publisher;
    private final IVerificationTokenRepository verificationTokenRepository;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("")
                .password("")
                .email("")
                .build();
        model.addAttribute("registerRequest", registerRequest);

        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid RegisterRequest request, BindingResult result, HttpServletRequest httpServletRequest, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("registerRequest", request);
            return "auth/register";
        }

        Usuario usuario = usuarioService.registrarUsuario(request);

        // Publicar el evento de registro de usuario
        try {
            publisher.publishEvent(new UsuarioRegistradoEvent(usuario, applicationUrl(httpServletRequest)));
        } catch (Exception e) {
            model.addAttribute("registerRequest", request);
            model.addAttribute("error", "Error al enviar el correo de verificación.");
            return "auth/register";
        }

        return "redirect:/auth/login";
    }

    @GetMapping("/register/verifyEmail")
    public String verifyEmail(String token, Model model) {
        String message;

        if (token == null) {
            message = "El token de verificación no es válido.";
            model.addAttribute("message", message);
            return "auth/registerSuccess";
        }

        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken.isPresent()) {
            Usuario usuario = verificationToken.get().getUsuario();
            if (usuario.isEnabled()) {
                message = "Esta cuenta ya ha sido verificada, por favor, vuelva al login.";
            } else {
                usuarioService.validarToken(token);
                message = "Su cuenta ha sido verificada con éxito. Ahora puede iniciar sesión.";
            }
        } else {
            message = "El token de verificación no es válido.";
        }

        model.addAttribute("message", message);
        return "auth/registerSuccess";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
