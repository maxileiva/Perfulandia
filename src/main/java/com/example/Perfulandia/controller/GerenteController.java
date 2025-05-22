package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;




@RestController
@RequestMapping("/api/gerente")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    @PostMapping
    public Gerente createGerente(@RequestBody Gerente gerente) {
        return gerenteService.createGerente(gerente);
    }

    @GetMapping
    public List<Gerente> getAllGerentes() {
        return gerenteService.getAllGerentes();
    }

    @PutMapping("{id}")
    public Gerente actualizarGerente(@PathVariable Integer id, @RequestBody Gerente gerente) {
    gerente.setId(id);
    return gerenteService.updateGerente(gerente);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGerente(@PathVariable Integer id) {
        gerenteService.deleteGerente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    

}
