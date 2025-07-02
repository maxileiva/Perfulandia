package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> findById(Integer id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente updateCliente(Cliente cliente) {
        if (cliente.getId() != null && clienteRepository.existsById(cliente.getId().intValue())) {
            return clienteRepository.save(cliente);
        }
        return null;
    }

    public Optional<Cliente> obtenerClientePorId(Integer id) {
        return clienteRepository.findById(id);
    }

    public void eliminarCliente(Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cliente no encontrado con ID: " + id + " para eliminar.");
        }
    }
}
