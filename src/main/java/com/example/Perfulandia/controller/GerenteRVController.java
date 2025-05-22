package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.GerenteRVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gerente/reporte/pedido")

public class GerenteRVController {

    @Autowired
    private GerenteRVService gerenteRVService;

    @GetMapping
    public List<Pedido> getAllPedido() {
        return gerenteRVService.getAllPedido();
    }
    
}
