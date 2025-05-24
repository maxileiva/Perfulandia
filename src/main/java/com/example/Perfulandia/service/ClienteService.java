package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
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

    public Cliente updateCliente(Cliente cliente) {
    return clienteRepository.save(cliente);
    }   

    
}







