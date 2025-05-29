package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;    

    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente getClienteId(Integer id) {
    Optional<Cliente> cliente = clienteRepository.findById(id);
    return cliente.orElse(null);
    }

    // Integer id;


    public Cliente updateCliente(Cliente cliente) {
    return clienteRepository.save(cliente);
    }

    public List<Cliente> listarCliente() {
    
        throw new UnsupportedOperationException("Unimplemented method 'listarCliente'");
    }

public Optional<Cliente> obtenerClientePorId(Integer id) {
    return clienteRepository.findById(id);
}

    

    public Cliente actualizarCliente(Integer id, Cliente cliente) {
       
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCliente'");
    }

    public void eliminarCliente(Integer id) {
      
        throw new UnsupportedOperationException("Unimplemented method 'eliminarCliente'");
    }

    public List<Cliente> listarAdministrador() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarAdministrador'");
    }   

    
}

