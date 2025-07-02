package com.example.Perfulandia.assemblers;

import com.example.Perfulandia.controller.ClienteController;
import com.example.Perfulandia.controller.AdministradorController; // Necesario para enlazar a listarClientes
import com.example.Perfulandia.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).buscaCliente(cliente.getId())).withSelfRel(),
                // Enlace a la lista de clientes general (ej. desde el AdminController)
                linkTo(methodOn(AdministradorController.class).listarClientes()).withRel("clientes")
        );
    }
}