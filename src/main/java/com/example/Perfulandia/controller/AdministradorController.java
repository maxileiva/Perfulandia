package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.example.Perfulandia.assemblers.ClienteModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/admin/clientes")
public class AdministradorController {

    private final ClienteService clienteService;
    private final ClienteModelAssembler assembler;

    public AdministradorController(ClienteService clienteService, ClienteModelAssembler assembler) {
        this.clienteService = clienteService;
        this.assembler = assembler;
    }

    @Operation(summary = "Listar clientes", description = "Obtiene una lista de todos los clientes registrados en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener la lista de clientes")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();

        List<EntityModel<Cliente>> clientesConEnlaces = clientes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<Cliente>> collectionModel = CollectionModel.of(clientesConEnlaces,
                linkTo(methodOn(AdministradorController.class).listarClientes()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Obtiene un cliente específico por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado y obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado con el ID especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener el cliente")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorId(id.intValue());

        return cliente.map(c -> {
            EntityModel<Cliente> entityModel = assembler.toModel(c);
            entityModel.add(linkTo(methodOn(AdministradorController.class).actualizar(id, c)).withRel("actualizar"));
            entityModel.add(linkTo(methodOn(AdministradorController.class).eliminar(id)).withRel("eliminar"));
            return ResponseEntity.ok(entityModel);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza la información de un cliente existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida: el ID del cliente no coincide"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado para actualizar"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al actualizar el cliente")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (cliente.getId() != null && !cliente.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        cliente.setId(id);

        Cliente actualizado = clienteService.updateCliente(cliente);

        if (actualizado != null) {
            EntityModel<Cliente> entityModel = assembler.toModel(actualizado);
            entityModel.add(linkTo(methodOn(AdministradorController.class).obtenerPorId(id)).withSelfRel());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado para eliminar"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al eliminar el cliente")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id.intValue());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
