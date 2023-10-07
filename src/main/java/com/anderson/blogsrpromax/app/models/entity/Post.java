package com.anderson.blogsrpromax.app.models.entity;

import com.anderson.blogsrpromax.app.user.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario author;

     @PrePersist
    public void prePersist() {
         fechaCreacion = new Date();
    }
}
