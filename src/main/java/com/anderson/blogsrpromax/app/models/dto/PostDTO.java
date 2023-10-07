package com.anderson.blogsrpromax.app.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PostDTO {

    @NotBlank(message = "El título es requerido.")
    @Length(min = 2, max = 30, message = "El título debe tener entre 2 y 30 caracteres.")
    private String title;

    @NotBlank(message = "El contenido es requerido.")
    @Length(min = 2, max = 1000, message = "El contenido debe tener entre 2 y 120 caracteres.")
    private String content;
}
