package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Factura;
import com.example.Perfulandia.service.FacturaService;
import com.example.Perfulandia.assemblers.FacturaModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    private final FacturaModelAssembler assembler;

    public FacturaController(FacturaModelAssembler assembler) {
        this.assembler = assembler;
    }

    @Operation(summary = "Crear una nueva factura", description = "Crea una factura nueva en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Factura creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Factura>> crearFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.crearFactura(factura);
        return new ResponseEntity<>(assembler.toModel(nuevaFactura), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar factura existente", description = "Actualiza una factura existente por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> actualizarFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        try {
            Factura actualizada = facturaService.actualizarFactura(id, factura);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar factura", description = "Elimina una factura por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Factura eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Integer id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtener factura por ID", description = "Obtiene una factura específica mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura encontrada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> obtenerFactura(@PathVariable Integer id) {
        return facturaService.obtenerFacturaPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar todas las facturas", description = "Devuelve una lista de todas las facturas en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Factura>>> listarFacturas() {
        List<Factura> facturas = facturaService.listarFacturas();

        List<EntityModel<Factura>> facturasConEnlaces = facturas.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(facturasConEnlaces,
                        linkTo(methodOn(FacturaController.class).listarFacturas()).withSelfRel()));
    }
}
