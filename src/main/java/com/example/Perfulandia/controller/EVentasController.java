package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.service.EVentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventas/stock")



public class EVentasController {

    @Autowired
    private GerenteService gerenteService;

     @GetMapping
    public List<Gerente> getAllGerentes() {
        return gerenteService.getAllGerentes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gerente> obtenerGerente(@PathVariable Integer id) {
        return gerenteService.obtenerGerentePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

