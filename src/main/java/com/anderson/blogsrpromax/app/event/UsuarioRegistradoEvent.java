package com.anderson.blogsrpromax.app.event;

import com.anderson.blogsrpromax.app.user.Usuario;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class UsuarioRegistradoEvent extends ApplicationEvent {

    private Usuario usuario;
    private String applicationUrl;

    public UsuarioRegistradoEvent(Usuario usuario, String applicationUrl) {
        super(usuario);
        this.usuario = usuario;
        this.applicationUrl = applicationUrl;
    }
}
