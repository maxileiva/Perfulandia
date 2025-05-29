package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Crear cliente
    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Obtener cliente por ID
    public Optional<Cliente> obtenerClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

    // Listar todos los clientes
    public List<Cliente> listarCliente() {
        return clienteRepository.findAll();
    }

    // Actualizar cliente
    public Cliente actualizarCliente(Integer id, Cliente clienteActualizado) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente clienteExistente = clienteOptional.get();
            clienteExistente.setRut(clienteActualizado.getRut());
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setApellido(clienteActualizado.getApellido());
            clienteExistente.setTelefono(clienteActualizado.getTelefono());
            clienteExistente.setCorreo(clienteActualizado.getCorreo());
            clienteExistente.setDireccion(clienteActualizado.getDireccion());
            clienteExistente.setRol(clienteActualizado.getRol());

            return clienteRepository.save(clienteExistente);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
    }

    // Eliminar cliente
    public void eliminarCliente(Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
    }
}
