package com.example.Perfulandia.service;

import com.example.usuarios.model.Usuario;
import com.example.Perfulandia.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Optional<Usuario> obtenerUsuario(Long id) {
        return repository.findById(id);
    }

    public Optional<Usuario> actualizarUsuario(Long id, Usuario nuevo) {
        return repository.findById(id).map(usuario -> {
            usuario.setUsername(nuevo.getUsername());
            usuario.setNombre(nuevo.getNombre());
            usuario.setApellido(nuevo.getApellido());
            usuario.setCorreo(nuevo.getCorreo());
            usuario.setRol(nuevo.getRol());
            usuario.setContraseña(nuevo.getContraseña());
            return repository.save(usuario);
        });
    }

    public boolean eliminarUsuario(Long id) {
        return repository.findById(id).map(usuario -> {
            repository.delete(usuario);
            return true;
        }).orElse(false);
    }
}
