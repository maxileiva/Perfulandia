package com.example.Cliente.service;

import com.example.Cliente.model.Cliente;
import com.example.Cliente.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public Optional<Cliente> obtenerCliente(Long id) {
        return repository.findById(id);
    }

    public Cliente crearCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> actualizarCliente(Long id, Cliente detalles) {
        return repository.findById(id).map(cliente -> {
            cliente.setRut(detalles.getRut());
            cliente.setNombre(detalles.getNombre());
            cliente.setApellido(detalles.getApellido());
            cliente.setCorreo(detalles.getCorreo());
            cliente.setTelefono(detalles.getTelefono());
            cliente.setContraseña(detalles.getContraseña());
            return repository.save(cliente);
        });
    }

    public boolean eliminarCliente(Long id) {
        return repository.findById(id).map(cliente -> {
            repository.delete(cliente);
            return true;
        }).orElse(false);
    }
}
