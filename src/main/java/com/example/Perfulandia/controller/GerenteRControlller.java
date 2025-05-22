package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/gerente/reporte/stock")

public class GerenteRControlller {

    @Autowired
    private GerenteRService gerenteRService;

    @GetMapping
    public List<Gerente> getAllGerentes() {
        return gerenteRService.getAllGerentes();
    }
    
}
