package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.GerenteRVController;
import com.example.Perfulandia.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador para convertir objetos Pedido en EntityModel<Pedido>
 * con enlaces HATEOAS específicos para el GerenteRVController (reportes de pedidos).
 */
@Component
public class GerenteRVModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    /**
     * Convierte un objeto Pedido en un EntityModel de Pedido, añadiendo enlaces HATEOAS.
     *
     * @param pedido El objeto Pedido a convertir.
     * @return Un EntityModel que contiene el Pedido y enlaces relevantes para el GerenteRVController.
     */
    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        // Asumiendo que la clase Pedido tiene un método getId() para obtener su ID.
        // Si el ID se llama diferente (ej. getId_pedido()), por favor ajusta esta línea.
        // Si no hay un endpoint GET para un solo Pedido, el self-rel podría ser omitido o apuntar a la colección.
        // Para este caso, se asume que un método para obtener por ID existe o existirá.
        return EntityModel.of(pedido,
                // Enlace a la colección de pedidos: apunta al método 'getAllPedido' del GerenteRVController
                linkTo(methodOn(GerenteRVController.class).getAllPedido()).withRel("reporte-pedidos")
        );
    }
}
