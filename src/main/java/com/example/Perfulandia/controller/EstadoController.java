package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Estado;
import com.example.Perfulandia.service.LogisticaService;
import com.example.Perfulandia.assemblers.EstadoModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/estado")
public class EstadoController {

    @Autowired
    private LogisticaService estadoService;

    @Autowired
    private com.example.Perfulandia.repository.EstadoRepository estadoRepository;

    private final EstadoModelAssembler assembler;

    public EstadoController(EstadoModelAssembler assembler) {
        this.assembler = assembler;
    }

    /**
     * Obtiene una lista de todos los estados de pedidos registrados en el sistema.
     * Ahora devuelve una CollectionModel con EntityModels de Estado y enlaces HATEOAS.
     *
     * @return ResponseEntity con una CollectionModel de EntityModel<Estado> (HTTP 200 OK).
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Estado>>> obtenerTodos() {
        List<Estado> estados = estadoService.obtenerTodos();
        
        List<EntityModel<Estado>> estadosConEnlaces = estados.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(estadosConEnlaces,
                        linkTo(methodOn(EstadoController.class).obtenerTodos()).withSelfRel()));
    }

    /**
     * Obtiene un estado de pedido específico por su ID.
     * Ahora devuelve un EntityModel de Estado con enlaces HATEOAS.
     *
     * @param id El ID del estado de pedido a buscar.
     * @return ResponseEntity con el EntityModel<Estado> si se encuentra (HTTP 200 OK),
     * o ResponseEntity con estado NOT_FOUND (HTTP 404) si no se encuentra.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Estado>> obtenerPorId(@PathVariable Integer id) {
        return estadoService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza el estado de un pedido existente.
     * Solo permite actualizar el 'estadoPedido' a "En Camino" o "Entregado".
     * Si el estado proporcionado no es válido, devuelve un error BAD_REQUEST.
     * Ahora devuelve un EntityModel de Estado con enlaces HATEOAS en caso de éxito.
     *
     * @param id El ID del estado de pedido a actualizar.
     * @param estado El objeto Estado con la información actualizada, incluyendo el nuevo estadoPedido y el RUT.
     * @return ResponseEntity con el EntityModel<Estado> actualizado si la operación es exitosa (HTTP 200 OK),
     * ResponseEntity con estado NOT_FOUND (HTTP 404) si el ID no existe,
     * o ResponseEntity con estado BAD_REQUEST (HTTP 400) si el estado proporcionado no es válido.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable Integer id, @RequestBody Estado estado) {

        String nuevoEstadoPedido = estado.getEstadoPedido();
        if ("En Camino".equals(nuevoEstadoPedido) || "Entregado".equals(nuevoEstadoPedido)) {
            return estadoRepository.findById(id)
                    .map(estadoExistente -> {
                        estadoExistente.setRut(estado.getRut());
                        estadoExistente.setEstadoPedido(nuevoEstadoPedido);

                        Estado estadoActualizado = estadoRepository.save(estadoExistente);
                        return ResponseEntity.ok(assembler.toModel(estadoActualizado));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ingresa un estado válido. Los valores permitidos son 'En Camino' o 'Entregado'.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
