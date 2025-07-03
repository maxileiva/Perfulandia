package com.example.Perfulandia.service;
import com.example.Perfulandia.repository.GerentePedidoRepository;
import com.example.Perfulandia.model.Pedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class GerenteRVService {

    @Autowired
    private GerentePedidoRepository gerentePedidoRepository;

    public List<Pedido> getAllPedido() {
        return gerentePedidoRepository.findAllWithDetalles();
    }

    public Pedido findById(Integer id) {
        return gerentePedidoRepository.findById(id).orElse(null);
    }
}


