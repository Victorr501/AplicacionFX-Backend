package com.aplicacion.backend.aplicacionfx_backend.controlador;


import com.aplicacion.backend.aplicacionfx_backend.modelo.Usuario;
import com.aplicacion.backend.aplicacionfx_backend.servicio.UsuarioServiceBackend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Declara que esta clase es un controlador REST de Spring
@RequestMapping("/api/usuarios") // Define el prefijo de URL para todos los endpoints de este controlador
@CrossOrigin(origins = "http://localhost:8081") // Importante para permitir CORS desde tu frontend JavaFX
public class UsuarioControllerBackend {

    private final UsuarioServiceBackend usuarioService;

    @Autowired // Inyección de dependencias del servicio en el constructor
    public UsuarioControllerBackend(UsuarioServiceBackend usuarioService) {
        this.usuarioService = usuarioService;
    }

    // --- Endpoints CRUD ---

    // GET: /api/usuarios
    // Obtiene todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // GET: /api/usuarios/{id}
    // Obtiene un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findUsuarioById(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK)) // Si se encuentra, devuelve 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Si no se encuentra, devuelve 404 Not Found
    }

    // POST: /api/usuarios
    // Crea un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED); // Devuelve 201 Created
    }

    // PUT: /api/usuarios/{id}
    // Actualiza un usuario existente por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        // Asegurarse de que el ID del usuario en el cuerpo coincida con el ID de la URL
        if (usuario.getId() != null && !usuario.getId().equals(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
        // Asegurarse de que el usuario a actualizar exista
        if (!usuarioService.findUsuarioById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
        usuario.setId(id); // Asegura que el ID de la entidad sea el de la URL
        Usuario usuarioActualizado = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK); // Devuelve 200 OK
    }

    // DELETE: /api/usuarios/{id}
    // Elimina un usuario por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioService.deleteUsuario(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Devuelve 204 No Content (eliminado exitosamente)
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 Not Found si no existe
    }


}
