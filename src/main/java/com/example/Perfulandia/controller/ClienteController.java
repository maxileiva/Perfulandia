package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.service.ClienteService;
import com.example.Perfulandia.assemblers.ClienteModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteModelAssembler assembler;

    public ClienteController(ClienteService clienteService, ClienteModelAssembler assembler) {
        this.clienteService = clienteService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        Map<String, String> errors = new HashMap<>();

        if (cliente.getRut() == null || cliente.getRut().length() != 9) {
            errors.put("rut", "El RUT debe tener exactamente 9 caracteres.");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().length() != 9) {
            errors.put("telefono", "El teléfono debe tener exactamente 9 caracteres.");
        }
        if (cliente.getCorreo() == null || !cliente.getCorreo().endsWith("@gmail.com")) {
            errors.put("correo", "El correo debe tener formato @gmail.com.");
        }
        try {
            int rolNumerico = Integer.parseInt(cliente.getRol());
            if (rolNumerico < 1 || rolNumerico > 5) {
                errors.put("rol", "El rol debe ser un número entre 1 y 5.");
            }
        } catch (NumberFormatException e) {
            errors.put("rol", "El rol debe ser un valor numérico entre 1 y 5.");
        }

        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Cliente nuevoCliente = clienteService.createCliente(cliente);
        EntityModel<Cliente> entityModel = assembler.toModel(nuevoCliente);

        // Agregar enlace para obtener el cliente creado
        entityModel.add(linkTo(methodOn(ClienteController.class).buscaCliente(nuevoCliente.getId())).withSelfRel());

        return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<EntityModel<Cliente>> buscaCliente(@PathVariable Long id) {
        return clienteService.findById(id.intValue())
                .map(c -> {
                    EntityModel<Cliente> entityModel = assembler.toModel(c);
                    // Agregar enlace para actualizar cliente
                    entityModel.add(linkTo(methodOn(ClienteController.class).actualizarCliente(id, c)).withRel("actualizar"));
                    return ResponseEntity.ok(entityModel);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        if (cliente.getId() != null && !cliente.getId().equals(id)) {
            Map<String, String> errorIdMismatch = new HashMap<>();
            errorIdMismatch.put("id", "El ID en la ruta no coincide con el ID en el cuerpo de la solicitud.");
            return new ResponseEntity<>(errorIdMismatch, HttpStatus.BAD_REQUEST);
        }

        cliente.setId(id);
        Map<String, String> errors = new HashMap<>();
        if (cliente.getRut() == null || cliente.getRut().length() != 9) {
            errors.put("rut", "El RUT debe tener exactamente 9 caracteres.");
        }
        if (cliente.getTelefono() == null || cliente.getTelefono().length() != 9) {
            errors.put("telefono", "El teléfono debe tener exactamente 9 caracteres.");
        }
        if (cliente.getCorreo() == null || !cliente.getCorreo().endsWith("@gmail.com")) {
            errors.put("correo", "El correo debe tener formato @gmail.com.");
        }
        try {
            int rolNumerico = Integer.parseInt(cliente.getRol());
            if (rolNumerico < 1 || rolNumerico > 5) {
                errors.put("rol", "El rol debe ser un número entre 1 y 5.");
            }
        } catch (NumberFormatException e) {
            errors.put("rol", "El rol debe ser un valor numérico entre 1 y 5.");
        }
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Cliente updatedCliente = clienteService.updateCliente(cliente);

        if (updatedCliente != null) {
            EntityModel<Cliente> entityModel = assembler.toModel(updatedCliente);
            // Agregar enlace self
            entityModel.add(linkTo(methodOn(ClienteController.class).buscaCliente(id)).withSelfRel());
            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
