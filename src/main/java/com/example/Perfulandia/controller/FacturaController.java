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

    /**
     * Crea una nueva factura en el sistema.
     * Ahora devuelve un EntityModel de Factura con enlaces HATEOAS.
     *
     * @param factura El objeto Factura a crear, recibido en el cuerpo de la solicitud.
     * @return ResponseEntity con el EntityModel<Factura> creado y HttpStatus.CREATED (201) si es exitoso.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Factura>> crearFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.crearFactura(factura);
        return new ResponseEntity<>(assembler.toModel(nuevaFactura), HttpStatus.CREATED);
    }

    /**
     * Actualiza una factura existente por su ID.
     * Ahora devuelve un EntityModel de Factura con enlaces HATEOAS en caso de éxito.
     *
     * @param id El ID de la factura a actualizar.
     * @param factura El objeto Factura con la información actualizada.
     * @return ResponseEntity con el EntityModel<Factura> actualizado si la operación es exitosa (HTTP 200 OK),
     * o ResponseEntity con estado NOT_FOUND (HTTP 404) si la factura no existe.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> actualizarFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        try {
            Factura actualizada = facturaService.actualizarFactura(id, factura);
            return ResponseEntity.ok(assembler.toModel(actualizada)); // Usa el ensamblador para convertir a EntityModel
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una factura por su ID.
     *
     * @param id El ID de la factura a eliminar.
     * @return ResponseEntity con estado NO_CONTENT (HTTP 204) si la eliminación es exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Integer id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene una factura específica por su ID.
     * Ahora devuelve un EntityModel de Factura con enlaces HATEOAS.
     *
     * @param id El ID de la factura a buscar.
     * @return ResponseEntity con el EntityModel<Factura> si se encuentra (HTTP 200 OK),
     * o ResponseEntity con estado NOT_FOUND (HTTP 404) si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Factura>> obtenerFactura(@PathVariable Integer id) {
        return facturaService.obtenerFacturaPorId(id)
                .map(assembler::toModel) // Usa el ensamblador para convertir a EntityModel
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene una lista de todas las facturas registradas en el sistema.
     * Ahora devuelve una CollectionModel con EntityModels de Factura y enlaces HATEOAS.
     *
     * @return ResponseEntity con una CollectionModel de EntityModel<Factura> (HTTP 200 OK).
     */
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
