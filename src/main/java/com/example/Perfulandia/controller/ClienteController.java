package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.service.ClienteService;
import com.example.Perfulandia.assemblers.ClienteModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteModelAssembler assembler;

    public ClienteController(ClienteService clienteService, ClienteModelAssembler assembler) {
        this.clienteService = clienteService;
        this.assembler = assembler;
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente en el sistema con registro en base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
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
        return new ResponseEntity<>(assembler.toModel(nuevoCliente), HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar cliente por ID", description = "Busca un cliente por su ID en base de datos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> buscaCliente(@PathVariable Long id) {
        return clienteService.findById(id.intValue())
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todos los clientes", description = "Obtiene una lista con todos los clientes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente"),
    })
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente existente por ID (actualiza bd)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
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
            return ResponseEntity.ok(assembler.toModel(updatedCliente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}