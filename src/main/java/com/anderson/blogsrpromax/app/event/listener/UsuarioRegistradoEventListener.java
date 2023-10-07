package com.anderson.blogsrpromax.app.event.listener;

import com.anderson.blogsrpromax.app.event.UsuarioRegistradoEvent;
import com.anderson.blogsrpromax.app.services.IUsuarioService;
import com.anderson.blogsrpromax.app.user.Usuario;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuarioRegistradoEventListener implements ApplicationListener<UsuarioRegistradoEvent> {

    private final IUsuarioService usuarioService;
    private final JavaMailSender mailSender;

    @Value("${email.sender}")
    private String emailBlogSrPromax;

    @Override
    public void onApplicationEvent(UsuarioRegistradoEvent event) {
        // 1. Obtener el usuario registrado
        Usuario usuario = event.getUsuario();

        // 2. Generar token de verificación para el usuario
        String token = UUID.randomUUID().toString();

        // 3. Guardar el token de verificación del usuario
        usuarioService.guardarTokenVerificacion(usuario, token);

        // 4. Crear la url de verificación del usuario para luego enviarla por email
        String url = event.getApplicationUrl() + "/auth/register/verifyEmail?token=" + token;

        // 5. Enviar el email
        try {
            enviarEmailDeVerificacion(usuario, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public void enviarEmailDeVerificacion(Usuario usuario, String url) throws MessagingException, UnsupportedEncodingException {
        String asunto = "Email de Verificación";
        String senderName = "Registro de Usuarios Javalearn";
        String contenido = "<p> Hola, " + usuario.getUsername() + ", </p>" +
                "<p>Gracias por registrarse con nosotros, " + " " +
                "Por favor, haga clic en el siguiente enlace para verificar su registro.</p>" +
                "<a href=\"" + url + "\">Verifique su email para activar su cuenta</a>" +
                "<p>Gracias <br> Registro de Usuarios BlogSrPromax</p>";

        // Crear un objeto MimeMessage que se utilizará para enviar un correo electrónico
        MimeMessage message = mailSender.createMimeMessage();

        // Crear un objeto MimeMessageHelper para facilitar la configuración del mensaje de correo electrónico
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(emailBlogSrPromax, senderName); // El remitente es el email de Javalearn
        messageHelper.setTo(usuario.getEmail()); // El destinatario es el email del usuario
        messageHelper.setSubject(asunto); // El asunto del email
        messageHelper.setText(contenido, true); // El contenido del email en html
        mailSender.send(message); // Enviar el email
    }
}
