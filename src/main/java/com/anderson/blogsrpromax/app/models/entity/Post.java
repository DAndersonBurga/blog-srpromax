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

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String content;

    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Usuario author;

     @PrePersist
    public void prePersist() {
         fechaCreacion = new Date();
    }
}
