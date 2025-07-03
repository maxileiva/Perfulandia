package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.GerenteRVController;
import com.example.Perfulandia.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador para convertir Pedido en EntityModel con enlaces HATEOAS.
 */
@Component
public class GerenteRVModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
                linkTo(methodOn(GerenteRVController.class).getAllPedido()).withRel("reporte-pedidos"),
                linkTo(methodOn(GerenteRVController.class).getPedidoById(pedido.getId_pedido())).withSelfRel()
        );
    }
}
