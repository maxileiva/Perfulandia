package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Estado;
import com.example.Perfulandia.service.LogisticaService;
import com.example.Perfulandia.assemblers.EstadoModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(EstadoController.class)
public class EstadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogisticaService logisticaService;

    @MockBean
    private EstadoModelAssembler assembler;

    private ObjectMapper objectMapper;
    private Estado estado;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        estado = new Estado();
        estado.setId_estado(1);
        estado.setRut("12345678-9");
        estado.setEstadoPedido("En Camino");
    }

    @Test
    void obtenerTodos() throws Exception {
        List<Estado> estados = Collections.singletonList(estado);
        when(logisticaService.obtenerTodos()).thenReturn(estados);

        EntityModel<Estado> estadoModel = EntityModel.of(estado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).obtenerPorId(estado.getId_estado())).withSelfRel());

        when(assembler.toModel(any(Estado.class))).thenReturn(estadoModel);

        mockMvc.perform(get("/estado"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._embedded.estadoList[0].rut").value(estado.getRut()))
                .andExpect(jsonPath("$._embedded.estadoList[0].estadoPedido").value(estado.getEstadoPedido()))
                .andExpect(jsonPath("$._embedded.estadoList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists())
                .andDo(print());
    }

    @Test
    void obtenerPorId() throws Exception {
        when(logisticaService.obtenerPorId(1)).thenReturn(Optional.of(estado));

        EntityModel<Estado> estadoModel = EntityModel.of(estado,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).obtenerPorId(estado.getId_estado())).withSelfRel());

        when(assembler.toModel(any(Estado.class))).thenReturn(estadoModel);

        mockMvc.perform(get("/estado/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.rut").value(estado.getRut()))
                .andExpect(jsonPath("$.estadoPedido").value(estado.getEstadoPedido()))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andDo(print());
    }
    

@Test
void updateEstado() throws Exception {
    // Mockear obtenerPorId para que devuelva el estado existente
    when(logisticaService.obtenerPorId(any(Integer.class))).thenReturn(Optional.of(estado));
    // Mockear updateEstado para que devuelva el estado actualizado
    when(logisticaService.updateEstado(any(Estado.class))).thenReturn(estado);

    EntityModel<Estado> estadoModel = EntityModel.of(estado,
        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstadoController.class).obtenerPorId(estado.getId_estado())).withSelfRel());

    when(assembler.toModel(any(Estado.class))).thenReturn(estadoModel);

    mockMvc.perform(put("/estado/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(estado)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.rut").value(estado.getRut()))
        .andExpect(jsonPath("$.estadoPedido").value(estado.getEstadoPedido()))
        .andExpect(jsonPath("$._links.self.href").exists())
        .andDo(print());
}

}