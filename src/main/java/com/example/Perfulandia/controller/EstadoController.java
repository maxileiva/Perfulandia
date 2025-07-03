package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Estado;
import com.example.Perfulandia.service.LogisticaService;
import com.example.Perfulandia.assemblers.EstadoModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@RestController
@RequestMapping("/estado")
public class EstadoController {

    @Autowired
    private LogisticaService estadoService;  // Cambié el nombre para que sea más claro que es el servicio

    private final EstadoModelAssembler assembler;

    public EstadoController(EstadoModelAssembler assembler) {
        this.assembler = assembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Estado>>> obtenerTodos() {
        List<Estado> estados = estadoService.obtenerTodos();
        List<EntityModel<Estado>> estadosConEnlaces = estados.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(estadosConEnlaces,
                linkTo(methodOn(EstadoController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Estado>> obtenerPorId(@PathVariable Integer id) {
        return estadoService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Integer id, @RequestBody Estado estado) {
        String nuevoEstadoPedido = estado.getEstadoPedido();
        if (!"En Camino".equals(nuevoEstadoPedido) && !"Entregado".equals(nuevoEstadoPedido)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ingresa un estado válido. Los valores permitidos son 'En Camino' o 'Entregado'.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Delega a servicio el update
        return estadoService.obtenerPorId(id)
                .map(estadoExistente -> {
                    estadoExistente.setRut(estado.getRut());
                    estadoExistente.setEstadoPedido(nuevoEstadoPedido);
                    Estado estadoActualizado = estadoService.updateEstado(estadoExistente);
                    return ResponseEntity.ok(assembler.toModel(estadoActualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}