package com.example.Perfulandia.service;

import java.util.List;
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
        estado.setPedido(pedidoGuardado);
        estado.setRut(pedidoGuardado.getRutc());
        estado.setEstadoPedido("En camino");

        estadoRepository.save(estado);

        return pedidoGuardado;
    }
    
    public List<Pedido> getPedidoRutc(String rutc) {
        return pedidoRepository.findByrutc(rutc); 
    }


    public Pedido getPedidoId_pedido(Integer id_pedido) {
    return pedidoRepository.findById(id_pedido).orElse(null);
}




   // Integer id_pedido;






}
