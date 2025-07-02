package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.GerenteRControlller;
import com.example.Perfulandia.model.Gerente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador para convertir objetos Gerente en EntityModel<Gerente>
 * con enlaces HATEOAS específicos para el GerenteRControlller (reportes de stock).
 */
@Component
public class GerenteRModelAssembler implements RepresentationModelAssembler<Gerente, EntityModel<Gerente>> {

    /**
     * Convierte un objeto Gerente en un EntityModel de Gerente, añadiendo enlaces HATEOAS.
     *
     * @param gerente El objeto Gerente a convertir.
     * @return Un EntityModel que contiene el Gerente y enlaces relevantes para el GerenteRControlller.
     */
    @Override
    public EntityModel<Gerente> toModel(Gerente gerente) {
        return EntityModel.of(gerente,
                // Enlace a la colección de gerentes: apunta al método 'getAllGerentes' del GerenteRControlller
                linkTo(methodOn(GerenteRControlller.class).getAllGerentes()).withRel("reporte-stock")
        );
    }
}
