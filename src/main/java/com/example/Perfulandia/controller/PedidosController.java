package com.example.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.model.Pedido;
import java.util.List;
import java.util.stream.Collectors;
import com.example.Perfulandia.service.PedidoService;
import com.example.Perfulandia.assemblers.PedidosModelAssembler;
import com.example.Perfulandia.service.GerenteService;

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

    /**
     * Crea un nuevo pedido en el sistema.
     * Ahora devuelve un EntityModel de Pedido con enlaces HATEOAS.
     *
     * @param pedido El objeto Pedido a crear, recibido en el cuerpo de la solicitud.
     * @return ResponseEntity con el EntityModel<Pedido> creado y HttpStatus.OK (200) si es exitoso.
     */
    @PostMapping
    public ResponseEntity<EntityModel<Pedido>> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
        return ResponseEntity.ok(assembler.toModel(nuevoPedido)); // Usar el ensamblador
    }

    /**
     * Busca pedidos por el RUT del cliente.
     * Ahora devuelve una CollectionModel con EntityModels de Pedido y enlaces HATEOAS.
     *
     * @param rutc El RUT del cliente para buscar sus pedidos.
     * @return ResponseEntity con una CollectionModel de EntityModel<Pedido> (HTTP 200 OK).
     */
    @GetMapping("{rutc}")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> buscaPedido(@PathVariable String rutc) {
        List<Pedido> pedidos = pedidoService.getPedidoRutc(rutc);
        
        List<EntityModel<Pedido>> pedidosConEnlaces = pedidos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(pedidosConEnlaces,
                        linkTo(methodOn(PedidosController.class).buscaPedido(rutc)).withSelfRel()));
    }

    /**
     * Busca un pedido específico por su ID de pedido.
     * Ahora devuelve un EntityModel de Pedido con enlaces HATEOAS.
     *
     * @param id_pedido El ID del pedido a buscar.
     * @return ResponseEntity con el EntityModel<Pedido> si se encuentra (HTTP 200 OK),
     * o ResponseEntity con estado NOT_FOUND (HTTP 404) si no se encuentra.
     */
    @GetMapping("/id/{id_pedido}")
    public ResponseEntity<EntityModel<Pedido>> buscaPedidoId_pedido(@PathVariable Integer id_pedido) {
        Pedido pedido = pedidoService.getPedidoId_pedido(id_pedido);
        if (pedido != null) {
            return ResponseEntity.ok(assembler.toModel(pedido)); // Usar el ensamblador
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


//Formato INGRESAR PEDIDOS
//{
 // "rutc": "218003065",
 // "fecha_pedido": "2025-05-23",
 // "neto": "200000",
 // "iva": "7000",
 // "total": 207000,
 // "detallePedido": [
  //  {
  //    "id_producto": "1",
   //   "descripcion": "Armani Stronger With You",
   //   "cantidad": "1",
    //  "valor": 98990.0
   // },
   // {
   //   "id_producto": "2",
   //   "descripcion": "Jean Paul Glautier Scandal",
   //   "cantidad": "1",
   //   "valor": 124990.0
  //  }
  //]
//}
