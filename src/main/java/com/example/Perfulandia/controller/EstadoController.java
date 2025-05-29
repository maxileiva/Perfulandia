package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Estado;
import com.example.Perfulandia.service.LogisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/estado")
public class EstadoController {

    @Autowired
    private LogisticaService estadoService;

    // Ver todos los estados
    @GetMapping
    public List<Estado> obtenerTodos() {
        return estadoService.obtenerTodos();
    }

    // Ver estado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estado> obtenerPorId(@PathVariable Integer id) {
        return estadoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por estado
    @GetMapping("/buscar")
    public List<Estado> buscarPorEstado(@RequestParam String estado) {
        return estadoService.buscarPorEstado(estado);
    }

    @PutMapping("/{id}")
    public Estado actualizarEstado(@PathVariable Integer id, @RequestBody Estado estado) {
    estado.setId_estado(id);
    return estadoService.updateEstado(estado);
}

}

   
