package com.example.Perfulandia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Perfulandia.model.Pedido;
import java.util.List;
import com.example.Perfulandia.service.PedidoService;



@RestController

@RequestMapping("/api/cliente/pedido")

public class PedidosController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.guardarPedido(pedido);
        return ResponseEntity.ok(nuevoPedido);
    }

    @GetMapping("{rutc}")
    public List<Pedido> buscaPedido(@PathVariable String rutc) {
        return pedidoService.getPedidoRutc(rutc);
    }

    @GetMapping("/id/{id_pedido}")
    public Pedido buscaPedidoId_pedido(@PathVariable Integer id_pedido) {
        return pedidoService.getPedidoId_pedido(id_pedido);
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
