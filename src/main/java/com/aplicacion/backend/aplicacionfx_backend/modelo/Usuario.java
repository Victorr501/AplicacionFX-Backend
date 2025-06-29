package com.aplicacion.backend.aplicacionfx_backend.modelo;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String rol;

    @Column(nullable = false)
    private Boolean activo;

}
