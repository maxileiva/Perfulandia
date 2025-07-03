package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.service.PedidoService;
import com.example.Perfulandia.assemblers.PedidosModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/cliente/pedido")
public class PedidosController {
    @Autowired
    private PedidoService pedidoService;

    private final PedidosModelAssembler assembler;

    @Autowired
    private GerenteService gerenteService;

    public PedidosController(PedidosModelAssembler assembler) {
        this.assembler = assembler;
    }

    @Operation(summary = "Crear pedido", description = "Crea un nuevo pedido en el sistema con los detalles proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida o datos incompletos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al crear el pedido")
    })
    @PostMapping
    public ResponseEntity<EntityModel<Pedido>> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
        EntityModel<Pedido> pedidoModel = assembler.toModel(nuevoPedido)
            .add(linkTo(methodOn(PedidosController.class).buscaPedido(nuevoPedido.getRutc())).withRel("pedidosDelCliente"))
            .add(linkTo(methodOn(PedidosController.class).getAllGerentes()).withRel("catalogoGerentes"));
        return ResponseEntity.ok(pedidoModel);
    }

    @Operation(summary = "Buscar pedidos por RUT", description = "Obtiene todos los pedidos asociados a un cliente identificado por su RUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedidos encontrados y listados exitosamente"),
        @ApiResponse(responseCode = "404", description = "No se encontraron pedidos para el RUT especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al buscar pedidos")
    })
    @GetMapping("{rutc}")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> buscaPedido(@PathVariable String rutc) {
        List<Pedido> pedidos = pedidoService.getPedidoRutc(rutc);

        List<EntityModel<Pedido>> pedidosConEnlaces = pedidos.stream()
                .map(p -> assembler.toModel(p)
                    .add(linkTo(methodOn(PedidosController.class).buscaPedido(rutc)).withRel("pedidosDelCliente"))
                    .add(linkTo(methodOn(PedidosController.class).getAllGerentes()).withRel("catalogoGerentes")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(pedidosConEnlaces,
                        linkTo(methodOn(PedidosController.class).buscaPedido(rutc)).withSelfRel(),
                        linkTo(methodOn(PedidosController.class).getAllGerentes()).withRel("catalogoGerentes")));
    }

    @Operation(summary = "Buscar pedido por ID", description = "Obtiene un pedido específico identificado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado y obtenido exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado con el ID especificado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener el pedido")
    })
    @GetMapping("/id/{id_pedido}")
    public ResponseEntity<EntityModel<Pedido>> buscaPedidoId_pedido(@PathVariable Integer id_pedido) {
        Pedido pedido = pedidoService.getPedidoId_pedido(id_pedido);
        if (pedido != null) {
            EntityModel<Pedido> pedidoModel = assembler.toModel(pedido)
                .add(linkTo(methodOn(PedidosController.class).buscaPedido(pedido.getRutc())).withRel("pedidosDelCliente"))
                .add(linkTo(methodOn(PedidosController.class).getAllGerentes()).withRel("catalogoGerentes"));
            return ResponseEntity.ok(pedidoModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar gerentes", description = "Obtiene el catálogo completo de gerentes disponibles")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de gerentes obtenido exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor al obtener la lista de gerentes")
    })
    @GetMapping("/catalogo")
    public ResponseEntity<CollectionModel<EntityModel<Gerente>>> getAllGerentes() {
        List<Gerente> gerentes = gerenteService.getAllGerentes();
        List<EntityModel<Gerente>> gerenteModels = gerentes.stream()
                .map(gerente -> EntityModel.of(gerente,
                        linkTo(methodOn(PedidosController.class).getAllGerentes()).withSelfRel()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                CollectionModel.of(gerenteModels,
                        linkTo(methodOn(PedidosController.class).getAllGerentes()).withSelfRel()));
    }
}
