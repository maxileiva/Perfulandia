package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.assemblers.GerenteModelAssembler; 
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

    @PostMapping
    public ResponseEntity<EntityModel<Gerente>> createGerente(@RequestBody Gerente gerente) {
        Gerente nuevoGerente = gerenteService.createGerente(gerente);
        EntityModel<Gerente> resource = assembler.toModel(nuevoGerente)
            .add(linkTo(methodOn(GerenteController.class).getAllGerentes()).withRel("gerentes"))
            .add(linkTo(methodOn(GerenteController.class).actualizarGerente(nuevoGerente.getId(), nuevoGerente)).withRel("actualizar"))
            .add(linkTo(methodOn(GerenteController.class).deleteGerente(nuevoGerente.getId())).withRel("eliminar"));
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGerente(@PathVariable Integer id) {
        gerenteService.deleteGerente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
