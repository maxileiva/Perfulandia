package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.GerenteController;
import com.example.Perfulandia.model.Gerente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class GerenteModelAssembler implements RepresentationModelAssembler<Gerente, EntityModel<Gerente>> {

    /**
     * Convierte un objeto Gerente en un EntityModel de Gerente, añadiendo enlaces HATEOAS.
     *
     * @param gerente El objeto Gerente a convertir.
     * @return Un EntityModel que contiene el Gerente y enlaces relevantes para el GerenteController.
     */
    @Override
    public EntityModel<Gerente> toModel(Gerente gerente) {
        return EntityModel.of(gerente,
                linkTo(methodOn(GerenteController.class).actualizarGerente(gerente.getId(), gerente)).withSelfRel(),
                linkTo(methodOn(GerenteController.class).getAllGerentes()).withRel("gerentes")
        );
    }
}