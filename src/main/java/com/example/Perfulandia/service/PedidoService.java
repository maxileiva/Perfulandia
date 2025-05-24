package com.example.Perfulandia.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Perfulandia.repository.PedidoRepository;
import com.example.Perfulandia.model.Estado;
import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.repository.EstadoRepository;

@Service

public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Pedido guardarPedido(Pedido pedido) {

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        Estado estado = new Estado();
        estado.setId_pedido(pedidoGuardado.getId_pedido().toString());
        estado.setRut(pedidoGuardado.getRutc());
        estado.setEstado_Pedido("En camino");

        estadoRepository.save(estado);

        return pedidoGuardado;


    }
}
