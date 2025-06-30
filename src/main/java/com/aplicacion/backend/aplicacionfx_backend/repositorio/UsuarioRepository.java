package com.aplicacion.backend.aplicacionfx_backend.repositorio;

import com.aplicacion.backend.aplicacionfx_backend.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // JpaRepository<T, ID> proporciona automáticamente métodos CRUD como:
    // - save(Usuario entity): Guarda o actualiza un usuario.
    // - findById(Long id): Busca un usuario por su ID.
    // - findAll(): Devuelve una lista de todos los usuarios.
    // - deleteById(Long id): Elimina un usuario por su ID.
    // - existsById(Long id): Verifica si un usuario con el ID existe.
}
