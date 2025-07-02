package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.EVentasController;
import com.example.Perfulandia.model.Gerente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EVentasModelAssembler implements RepresentationModelAssembler<Gerente, EntityModel<Gerente>> {

    /**
     * Convierte un objeto Gerente en un EntityModel de Gerente, añadiendo enlaces HATEOAS.
     *
     * @param gerente El objeto Gerente a convertir.
     * @return Un EntityModel que contiene el Gerente y enlaces relevantes para el EVentasController.
     */
    @Override
    public EntityModel<Gerente> toModel(Gerente gerente) {
        return EntityModel.of(gerente,
                linkTo(methodOn(EVentasController.class).obtenerGerente(gerente.getId())).withSelfRel(),
                linkTo(methodOn(EVentasController.class).getAllGerentes()).withRel("stock")
        );
    }
}
