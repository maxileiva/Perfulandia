package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.ClientePService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente/pedido")

public class ClientePController {

    @Autowired
    private ClientePService clientePService;

    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return clientePService.createPedido(pedido);
    }

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return clientePService.getAllPedidos();
    }

}
