package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Factura;
import com.example.Perfulandia.service.FacturaService;
import com.example.Perfulandia.assemblers.FacturaModelAssembler; 
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

    @PostMapping
    public ResponseEntity<EntityModel<Factura>> crearFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.crearFactura(factura);
        return new ResponseEntity<>(assembler.toModel(nuevaFactura), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> actualizarFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        try {
            Factura actualizada = facturaService.actualizarFactura(id, factura);
            return ResponseEntity.ok(assembler.toModel(actualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Integer id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> obtenerFactura(@PathVariable Integer id) {
        return facturaService.obtenerFacturaPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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
