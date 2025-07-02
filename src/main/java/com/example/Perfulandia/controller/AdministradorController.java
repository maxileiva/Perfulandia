package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.service.ClienteService;
import com.example.Perfulandia.assemblers.ClienteModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();

        List<EntityModel<Cliente>> clientesConEnlaces = clientes.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(clientesConEnlaces,
                        linkTo(methodOn(AdministradorController.class).listarClientes()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> obtenerPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.obtenerClientePorId(id.intValue());
        return cliente.map(assembler::toModel)
                      .map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (cliente.getId() != null && !cliente.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        cliente.setId(id);

        Cliente actualizado = clienteService.updateCliente(cliente);

        if (actualizado != null) {
            return ResponseEntity.ok(assembler.toModel(actualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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