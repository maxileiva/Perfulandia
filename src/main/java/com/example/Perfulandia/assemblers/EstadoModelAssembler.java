package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.EstadoController;
import com.example.Perfulandia.model.Estado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EstadoModelAssembler implements RepresentationModelAssembler<Estado, EntityModel<Estado>> {

    /**
     * Convierte un objeto Estado en un EntityModel de Estado, añadiendo enlaces HATEOAS.
     *
     * @param estado El objeto Estado a convertir.
     * @return Un EntityModel que contiene el Estado y enlaces relevantes para el EstadoController.
     */
    @Override
    public EntityModel<Estado> toModel(Estado estado) {
        return EntityModel.of(estado,
                linkTo(methodOn(EstadoController.class).obtenerPorId(estado.getId_estado())).withSelfRel(),
                linkTo(methodOn(EstadoController.class).obtenerTodos()).withRel("estados")
        );
    }
}
