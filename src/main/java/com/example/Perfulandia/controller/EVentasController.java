package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.assemblers.EVentasModelAssembler;
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

    /**
     * Servicio encargado de la lógica de negocio relacionada con los gerentes (stock).
     */
    @Autowired
    private GerenteService gerenteService;

    /**
     * Ensamblador para convertir objetos Gerente en EntityModel<Gerente> con enlaces HATEOAS.
     */
    private final EVentasModelAssembler assembler;
    
    public EVentasController(EVentasModelAssembler assembler) {
        this.assembler = assembler;
    }

    /**
     * Obtiene una lista de todos los elementos de stock (Gerente) disponibles.
     * Ahora devuelve una CollectionModel con EntityModels de Gerente y enlaces HATEOAS.
     *
     * @return ResponseEntity con una CollectionModel de EntityModel<Gerente> (HTTP 200 OK).
     */
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

    /**
     * Obtiene un elemento de stock (Gerente) específico por su ID.
     * Ahora devuelve un EntityModel de Gerente con enlaces HATEOAS.
     *
     * @param id El ID del elemento de stock a buscar.
     * @return ResponseEntity con el EntityModel<Gerente> si se encuentra (HTTP 200 OK),
     * o ResponseEntity con estado NOT_FOUND (HTTP 404) si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Gerente>> obtenerGerente(@PathVariable Integer id) {
        return gerenteService.obtenerGerentePorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
