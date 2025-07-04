package com.aplicacion.backend.aplicacionfx_backend.servicio;

import com.aplicacion.backend.aplicacionfx_backend.modelo.Usuario;
import com.aplicacion.backend.aplicacionfx_backend.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceBackend {
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // Inyección de dependencias del repositorio en el constructor
    @Autowired
    public UsuarioServiceBackend(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    // Metodos CRUD

    //Leer todos los usuarios
    public List<Usuario> findAllUsuarios(){
        return usuarioRepository.findAll();
    }

    //Leer un usuario por ID
    public Optional<Usuario> findUsuarioById(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario) {
        // Aquí puedes añadir lógica de negocio, por ejemplo:
        // - Validar el email
        // - Hashear la contraseña si no viene hasheada (¡muy importante!)
        // - Asignar roles por defecto
        String hashedPassword = passwordEncoder.encode(usuario.getPasswordHash());
        usuario.setPasswordHash(hashedPassword);

        return usuarioRepository.save(usuario);
    }

    // Verificar contraseña durante el inicio de sesión
    public boolean verificaContraseña(String contrasenaPlana, String contrasenaHasheadaAlmacenada){
        return passwordEncoder.matches(contrasenaPlana, contrasenaHasheadaAlmacenada);
    }

    // Eliminar un usuario por ID
    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Ejemplo de método de lógica de negocio adicional:
    public boolean deactivateUsuario(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
}
