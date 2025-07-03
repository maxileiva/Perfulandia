package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteRService;
import com.example.Perfulandia.assemblers.GerenteRModelAssembler;
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

    /**
     * Servicio encargado de la lógica de negocio relacionada con los reportes de gerentes (stock).
     */
    @Autowired
    private GerenteRService gerenteRService;

    /**
     * Ensamblador para convertir objetos Gerente en EntityModel<Gerente> con enlaces HATEOAS.
     */
    private final GerenteRModelAssembler assembler;

    // Constructor para inyectar el ensamblador
    public GerenteRController(GerenteRModelAssembler assembler) {
        this.assembler = assembler;
    }

    /**
     * Obtiene una lista de todos los elementos de stock (Gerente) disponibles para el reporte.
     * Ahora devuelve una CollectionModel con EntityModels de Gerente y enlaces HATEOAS.
     *
     * @return ResponseEntity con una CollectionModel de EntityModel<Gerente> (HTTP 200 OK).
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Gerente>>> getAllGerentes() {
        List<Gerente> gerentes = gerenteRService.getAllGerentes();
        List<EntityModel<Gerente>> gerentesConEnlaces = gerentes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(gerentesConEnlaces,
                        linkTo(methodOn(GerenteRController.class).getAllGerentes()).withSelfRel()));
    }
}
