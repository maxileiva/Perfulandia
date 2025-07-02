package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.GerenteRVService;
import com.example.Perfulandia.assemblers.GerenteRVModelAssembler; // Importar el ensamblador
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel; // Para la lista de recursos
import org.springframework.hateoas.EntityModel;    // Para un solo recurso
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/gerente/reporte/pedido")
public class GerenteRVController {

    /**
     * Servicio encargado de la lógica de negocio relacionada con los reportes de pedidos.
     */
    @Autowired
    private GerenteRVService gerenteRVService;

    /**
     * Ensamblador para convertir objetos Pedido en EntityModel<Pedido> con enlaces HATEOAS.
     */
    private final GerenteRVModelAssembler assembler;

    // Constructor para inyectar el ensamblador
    public GerenteRVController(GerenteRVModelAssembler assembler) {
        this.assembler = assembler;
    }

    /**
     * Obtiene una lista de todos los pedidos disponibles para el reporte.
     * Ahora devuelve una CollectionModel con EntityModels de Pedido y enlaces HATEOAS.
     *
     * @return ResponseEntity con una CollectionModel de EntityModel<Pedido> (HTTP 200 OK).
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getAllPedido() {
        List<Pedido> pedidos = gerenteRVService.getAllPedido();
        
        // Convierte cada Pedido en un EntityModel<Pedido> usando el ensamblador
        List<EntityModel<Pedido>> pedidosConEnlaces = pedidos.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        // Crea y devuelve una CollectionModel con los EntityModels y un enlace a sí misma
        return ResponseEntity.ok(
                CollectionModel.of(pedidosConEnlaces,
                        linkTo(methodOn(GerenteRVController.class).getAllPedido()).withSelfRel()));
    }
}
