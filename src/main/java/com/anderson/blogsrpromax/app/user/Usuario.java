package com.anderson.blogsrpromax.app.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true)
    private String username;
    private String password;

    @Column(length = 50, unique = true)
    @NaturalId(mutable = true)
    private String email;

    private String role;
    private boolean isEnabled = false; // Por defecto el usuario está deshabilitado
}
