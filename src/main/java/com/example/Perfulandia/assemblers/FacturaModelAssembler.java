package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.FacturaController;
import com.example.Perfulandia.model.Factura;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class FacturaModelAssembler implements RepresentationModelAssembler<Factura, EntityModel<Factura>> {

    /**
     * Convierte un objeto Factura en un EntityModel de Factura, añadiendo enlaces HATEOAS.
     *
     * @param factura El objeto Factura a convertir.
     * @return Un EntityModel que contiene la Factura y enlaces relevantes para el FacturaController.
     */
    @Override
    public EntityModel<Factura> toModel(Factura factura) {
        return EntityModel.of(factura,
                linkTo(methodOn(FacturaController.class).obtenerFactura(factura.getId_factura())).withSelfRel(),
                linkTo(methodOn(FacturaController.class).listarFacturas()).withRel("facturas")
        );
    }
}
