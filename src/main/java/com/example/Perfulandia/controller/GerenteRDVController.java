package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.DetallePedido;
import com.example.Perfulandia.service.GerenteRDVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gerente/reporte/dpedido")

public class GerenteRDVController {

    @Autowired
    private GerenteRDVService gerenteRDVService;

    @GetMapping
    public List<DetallePedido> getAllPedido() {
        return gerenteRDVService.getAllDetallePedido();
    }
    

}





