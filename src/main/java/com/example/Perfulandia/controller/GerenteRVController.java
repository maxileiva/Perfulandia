package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.GerenteRVService;
import com.example.Perfulandia.assemblers.GerenteRVModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/gerente/reporte/pedido")
public class GerenteRVController {

    @Autowired
    private GerenteRVService gerenteRVService;

    private final GerenteRVModelAssembler assembler;

    public GerenteRVController(GerenteRVModelAssembler assembler) {
        this.assembler = assembler;
    }

    /**
     * Lista todos los pedidos disponibles para el reporte.
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getAllPedido() {
        List<Pedido> pedidos = gerenteRVService.getAllPedido();

        List<EntityModel<Pedido>> pedidosConEnlaces = pedidos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(pedidosConEnlaces,
                        linkTo(methodOn(GerenteRVController.class).getAllPedido()).withSelfRel()));
    }

    /**
     * (Opcional) Obtener un pedido por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pedido>> getPedidoById(@PathVariable Integer id) {
        Pedido pedido = gerenteRVService.findById(id); // Este método debe existir en el servicio
        if (pedido != null) {
            return ResponseEntity.ok(assembler.toModel(pedido));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
