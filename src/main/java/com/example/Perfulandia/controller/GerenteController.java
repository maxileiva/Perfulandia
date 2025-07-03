package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.assemblers.GerenteModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/gerente")
public class GerenteController {

    @Autowired
    private GerenteService gerenteService;

    private final GerenteModelAssembler assembler;

    public GerenteController(GerenteModelAssembler assembler) {
        this.assembler = assembler;
    }

    @Operation(summary = "Crear un nuevo gerente", description = "Crea un nuevo gerente en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Gerente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Gerente>> createGerente(@RequestBody Gerente gerente) {
        Gerente nuevoGerente = gerenteService.createGerente(gerente);
        EntityModel<Gerente> resource = assembler.toModel(nuevoGerente)
            .add(linkTo(methodOn(GerenteController.class).getAllGerentes()).withRel("gerentes"))
            .add(linkTo(methodOn(GerenteController.class).actualizarGerente(nuevoGerente.getId(), nuevoGerente)).withRel("actualizar"))
            .add(linkTo(methodOn(GerenteController.class).deleteGerente(nuevoGerente.getId())).withRel("eliminar"));
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los gerentes", description = "Lista todos los gerentes disponibles")
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
                linkTo(methodOn(GerenteController.class).getAllGerentes()).withSelfRel(),
                linkTo(methodOn(GerenteController.class).createGerente(null)).withRel("crear"))
        );
    }

    @Operation(summary = "Actualizar gerente", description = "Actualiza la información de un gerente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gerente actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Gerente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Gerente>> actualizarGerente(@PathVariable Integer id, @RequestBody Gerente gerente) {
        gerente.setId(id);
        Gerente updatedGerente = gerenteService.updateGerente(gerente);

        if (updatedGerente != null) {
            EntityModel<Gerente> resource = assembler.toModel(updatedGerente)
                .add(linkTo(methodOn(GerenteController.class).getAllGerentes()).withRel("gerentes"))
                .add(linkTo(methodOn(GerenteController.class).deleteGerente(id)).withRel("eliminar"));
            return ResponseEntity.ok(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar gerente", description = "Elimina un gerente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Gerente eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Gerente no encontrado")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGerente(@PathVariable Integer id) {
        gerenteService.deleteGerente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
