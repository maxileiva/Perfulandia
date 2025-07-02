package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.AdministradorController;
import com.example.Perfulandia.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AdministradorModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    /**
     * Convierte un objeto Cliente en un EntityModel de Cliente, añadiendo enlaces HATEOAS.
     *
     * @param cliente El objeto Cliente a convertir.
     * @return Un EntityModel que contiene el Cliente y enlaces relevantes para el AdministradorController.
     */
    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(AdministradorController.class).obtenerPorId(cliente.getId())).withSelfRel(),
                linkTo(methodOn(AdministradorController.class).listarClientes()).withRel("clientes")
        );
    }
}
