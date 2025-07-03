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

    @PostMapping
    public ResponseEntity<EntityModel<Pedido>> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
        EntityModel<Pedido> pedidoModel = assembler.toModel(nuevoPedido)
            .add(linkTo(methodOn(PedidosController.class).buscaPedido(nuevoPedido.getRutc())).withRel("pedidosDelCliente"))
            .add(linkTo(methodOn(PedidosController.class).getAllGerentes()).withRel("catalogoGerentes"));
        return ResponseEntity.ok(pedidoModel);
    }

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


/* {
  "rutc": "218003065",
  "fecha_pedido": "2025-05-23",
  "neto": "200000",
  "iva": "7000",
  "total": 207000,
  "detallePedido": [
    {
      "id_producto": "1",
      "descripcion": "Armani Stronger With You",
      "cantidad": "1",
      "valor": 98990.0
    },
    {
      "id_producto": "2",
      "descripcion": "Jean Paul Glautier Scandal",
      "cantidad": "1",
      "valor": 124990.0
    }
  ]
}
*/