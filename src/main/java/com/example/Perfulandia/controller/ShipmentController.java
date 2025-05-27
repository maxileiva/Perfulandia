package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Shipment;
import com.example.Perfulandia.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public List<Shipment> getAllShipments() {
        return shipmentService.getAllShipments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getShipmentStatusById(@PathVariable Long id) {
        return shipmentService.getShipmentById(id)
                .map(shipment -> ResponseEntity.ok(shipment.getStatus()))
                .orElse(ResponseEntity.notFound().build());
    }

    // No hay método POST: no se permite crear envíos
}
