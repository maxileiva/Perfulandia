package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.PedidosController;
import com.example.Perfulandia.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Ensamblador para convertir objetos Pedido en EntityModel<Pedido>
 * con enlaces HATEOAS específicos para el PedidosController.
 */
@Component
public class PedidosModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    /**
     * Convierte un objeto Pedido en un EntityModel de Pedido, añadiendo enlaces HATEOAS.
     *
     * @param pedido El objeto Pedido a convertir.
     * @return Un EntityModel que contiene el Pedido y enlaces relevantes para el PedidosController.
     */
    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) {
        // Asumiendo que la clase Pedido tiene un método getId_pedido() para obtener su ID.
        // Si el ID se llama diferente, por favor ajusta esta línea.
        return EntityModel.of(pedido,
                // Enlace a sí mismo: apunta al método 'buscaPedidoId_pedido' del PedidosController para este pedido específico
                linkTo(methodOn(PedidosController.class).buscaPedidoId_pedido(pedido.getId_pedido())).withSelfRel(),
                // Enlace a la colección de pedidos por RUT: apunta al método 'buscaPedido' con el RUT del cliente
                linkTo(methodOn(PedidosController.class).buscaPedido(pedido.getRutc())).withRel("pedidos-por-cliente"),
                // Enlace a la colección general de pedidos (si existiera un endpoint para ello, aquí se asume la colección por RUT)
                // Si hubiera un endpoint como @GetMapping("/api/cliente/pedido") sin PathVariable, se podría enlazar a él.
                linkTo(methodOn(PedidosController.class).crearPedido(pedido)).withRel("crear-pedido") // Ejemplo de enlace a la creación
        );
    }
}
