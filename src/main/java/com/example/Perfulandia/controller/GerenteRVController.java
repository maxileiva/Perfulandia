package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.GerenteRVService;
import com.example.Perfulandia.assemblers.GerenteRVModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una colección con todos los pedidos para reportes de gerente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos obtenidos exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener pedidos")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getAllPedido() {
        List<Pedido> pedidos = gerenteRVService.getAllPedido();

        List<EntityModel<Pedido>> pedidosConEnlaces = pedidos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(pedidosConEnlaces,
                        linkTo(methodOn(GerenteRVController.class).getAllPedido()).withSelfRel(),
                        linkTo(methodOn(GerenteRVController.class).getAllPedido()).withRel("todosPedidosReporte")
                ));
    }

    @Operation(summary = "Obtener pedido por ID", description = "Obtiene el detalle de un pedido específico para reportes de gerente según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado y obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado con el ID especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener el pedido")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pedido>> getPedidoById(@PathVariable Integer id) {
        Pedido pedido = gerenteRVService.findById(id);
        if (pedido != null) {
            EntityModel<Pedido> pedidoModel = assembler.toModel(pedido)
                .add(linkTo(methodOn(GerenteRVController.class).getAllPedido()).withRel("todosPedidosReporte"));
            return ResponseEntity.ok(pedidoModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
