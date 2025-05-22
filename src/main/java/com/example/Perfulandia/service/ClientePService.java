package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.repository.ClientePRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ClientePService {

    @Autowired
    private ClientePRepository clientePRepository;

    public Pedido createPedido(@RequestBody Pedido pedido) {
        return clientePRepository.save(pedido);
    }

    public List<Pedido> getAllPedidos() {
        return clientePRepository.findAll();
    }
}
