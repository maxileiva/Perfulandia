package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteRService;
import com.example.Perfulandia.assemblers.GerenteRModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;    
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/gerente/reporte/stock")
public class GerenteRController {

    @Autowired
    private GerenteRService gerenteRService;

    private final GerenteRModelAssembler assembler;

    public GerenteRController(GerenteRModelAssembler assembler) {
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener reporte de stock de gerentes", description = "Devuelve una lista de gerentes con reporte de stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte de stock obtenido correctamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Gerente>>> getAllGerentes() {
        List<Gerente> gerentes = gerenteRService.getAllGerentes();
        List<EntityModel<Gerente>> gerentesConEnlaces = gerentes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
            CollectionModel.of(gerentesConEnlaces,
                linkTo(methodOn(GerenteRController.class).getAllGerentes()).withSelfRel(),
                linkTo(methodOn(GerenteRController.class).getAllGerentes()).withRel("reporteStock")
            )
        );
    }
}
