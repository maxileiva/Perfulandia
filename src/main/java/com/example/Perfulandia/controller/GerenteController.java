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

    /**
     * Crea un nuevo elemento de stock (Gerente) en el sistema.
     * Ahora devuelve un EntityModel de Gerente con enlaces HATEOAS.
     *
     * @param gerente El objeto Gerente a crear, recibido en el cuerpo de la solicitud.
     * @return ResponseEntity con el EntityModel<Gerente> creado y HttpStatus.CREATED (201) si es exitoso.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Gerente>> createGerente(@RequestBody Gerente gerente) {
        Gerente nuevoGerente = gerenteService.createGerente(gerente);
        return new ResponseEntity<>(assembler.toModel(nuevoGerente), HttpStatus.CREATED);
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
                        linkTo(methodOn(GerenteController.class).getAllGerentes()).withSelfRel()));
    }

    /**
     * Actualiza un elemento de stock (Gerente) existente por su ID.
     * Ahora devuelve un EntityModel de Gerente con enlaces HATEOAS en caso de éxito.
     *
     * @param id El ID del elemento de stock a actualizar.
     * @param gerente El objeto Gerente con la información actualizada.
     * @return Gerente El objeto Gerente actualizado.
     * @return ResponseEntity con el EntityModel<Gerente> actualizado si la operación es exitosa (HTTP 200 OK),
     * o ResponseEntity con estado NOT_FOUND (HTTP 404) si el gerente no existe.
     */
    @PutMapping("{id}")
    public ResponseEntity<EntityModel<Gerente>> actualizarGerente(@PathVariable Integer id, @RequestBody Gerente gerente) {
        gerente.setId(id);
        Gerente updatedGerente = gerenteService.updateGerente(gerente);

        if (updatedGerente != null) {
            return ResponseEntity.ok(assembler.toModel(updatedGerente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un elemento de stock (Gerente) por su ID.
     *
     * @param id El ID del elemento de stock a eliminar.
     * @return ResponseEntity con estado NO_CONTENT (HTTP 204) si la eliminación es exitosa.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGerente(@PathVariable Integer id) {
        gerenteService.deleteGerente(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
