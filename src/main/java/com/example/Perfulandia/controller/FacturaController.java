package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Factura;
import com.example.Perfulandia.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @PostMapping
    public Factura crearFactura(@RequestBody Factura factura) {
        return facturaService.crearFactura(factura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> actualizarFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        try {
            return ResponseEntity.ok(facturaService.actualizarFactura(id, factura));
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
    public ResponseEntity<Factura> obtenerFactura(@PathVariable Integer id) {
        return facturaService.obtenerFacturaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Factura> listarFacturas() {
        return facturaService.listarFacturas();
    }
}
