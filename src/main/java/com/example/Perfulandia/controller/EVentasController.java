package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.assemblers.EVentasModelAssembler;

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
@RequestMapping("/api/eventas/stock")
public class EVentasController {

    @Autowired
    private GerenteService gerenteService;

    private final EVentasModelAssembler assembler;

    public EVentasController(EVentasModelAssembler assembler) {
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todos los gerentes", description = "Devuelve una lista con todos los gerentes en stock")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de gerentes obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Gerente>>> getAllGerentes() {
        List<Gerente> gerentes = gerenteService.getAllGerentes();
        List<EntityModel<Gerente>> gerentesConEnlaces = gerentes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(gerentesConEnlaces,
                        linkTo(methodOn(EVentasController.class).getAllGerentes()).withSelfRel()));
    }

    @Operation(summary = "Obtener gerente por ID", description = "Obtiene un gerente específico mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gerente encontrado"),
        @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Gerente>> obtenerGerente(@PathVariable Integer id) {
        return gerenteService.obtenerGerentePorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
