package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Estado;
import com.example.Perfulandia.service.LogisticaService;
import com.example.Perfulandia.assemblers.EstadoModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    private LogisticaService estadoService;  // Servicio para estados

    private final EstadoModelAssembler assembler;

    public EstadoController(EstadoModelAssembler assembler) {
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todos los estados", description = "Devuelve una lista con todos los estados disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de estados obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Estado>>> obtenerTodos() {
        List<Estado> estados = estadoService.obtenerTodos();
        List<EntityModel<Estado>> estadosConEnlaces = estados.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(estadosConEnlaces,
                linkTo(methodOn(EstadoController.class).obtenerTodos()).withSelfRel()));
    }

    @Operation(summary = "Obtener estado por ID", description = "Obtiene un estado específico mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado encontrado"),
        @ApiResponse(responseCode = "404", description = "Estado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Estado>> obtenerPorId(@PathVariable Integer id) {
        return estadoService.obtenerPorId(id)
                .map(estado -> {
                    EntityModel<Estado> entityModel = assembler.toModel(estado);
                    // Enlace para actualizar este estado
                    entityModel.add(linkTo(methodOn(EstadoController.class).actualizarEstado(id, estado)).withRel("actualizar"));
                    return ResponseEntity.ok(entityModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar estado", description = "Actualiza un estado existente por ID. Solo valores 'En Camino' o 'Entregado' son válidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Estado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Integer id, @RequestBody Estado estado) {
        String nuevoEstadoPedido = estado.getEstadoPedido();
        if (!"En Camino".equals(nuevoEstadoPedido) && !"Entregado".equals(nuevoEstadoPedido)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ingresa un estado válido. Los valores permitidos son 'En Camino' o 'Entregado'.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        return estadoService.obtenerPorId(id)
                .map(estadoExistente -> {
                    estadoExistente.setRut(estado.getRut());
                    estadoExistente.setEstadoPedido(nuevoEstadoPedido);
                    Estado estadoActualizado = estadoService.updateEstado(estadoExistente);

                    EntityModel<Estado> entityModel = assembler.toModel(estadoActualizado);
                    // Enlace self para el estado actualizado
                    entityModel.add(linkTo(methodOn(EstadoController.class).obtenerPorId(id)).withSelfRel());

                    return ResponseEntity.ok(entityModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
