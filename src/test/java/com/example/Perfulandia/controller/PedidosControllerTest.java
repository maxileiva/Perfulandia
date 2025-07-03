package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.PedidoService;
import com.example.Perfulandia.service.GerenteService; // <- agregado
import com.example.Perfulandia.assemblers.PedidosModelAssembler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(PedidosController.class)
public class PedidosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @MockBean
    private PedidosModelAssembler assembler;

    @MockBean
    private GerenteService gerenteService; // <- MOCK agregado

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId_pedido(1);
        pedido.setRutc("218003065");
        pedido.setFecha_pedido("2025-05-23");
        pedido.setNeto("200000");
        pedido.setIva("7000");
        pedido.setTotal(new BigDecimal("207000"));
    }

    @Test
    void testCrearPedido() throws Exception {
        when(pedidoService.guardarPedido(any(Pedido.class))).thenReturn(pedido);
        when(assembler.toModel(any(Pedido.class))).thenReturn(EntityModel.of(pedido));

        mockMvc.perform(post("/api/cliente/pedido")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"rutc\":\"218003065\",\"fecha_pedido\":\"2025-05-23\",\"neto\":\"200000\",\"iva\":\"7000\",\"total\":207000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutc").value("218003065"))
                .andDo(print());
    }

    @Test
    void testBuscarPedidosPorRut() throws Exception {
        String rutc = "218003065";
        List<Pedido> pedidos = Collections.singletonList(pedido);

        when(pedidoService.getPedidoRutc(rutc)).thenReturn(pedidos);
        when(assembler.toModel(any(Pedido.class))).thenReturn(EntityModel.of(pedido));

        mockMvc.perform(get("/api/cliente/pedido/{rutc}", rutc))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pedidoList[0].rutc").value("218003065"))
                .andDo(print());
    }

    @Test
    void testBuscarPedidoPorId() throws Exception {
        when(pedidoService.getPedidoId_pedido(1)).thenReturn(pedido);
        when(assembler.toModel(any(Pedido.class))).thenReturn(EntityModel.of(pedido));

        mockMvc.perform(get("/api/cliente/pedido/id/{id_pedido}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rutc").value("218003065"))
                .andDo(print());
    }

    @Test
    void testGetAllGerentes() throws Exception {
        mockMvc.perform(get("/api/cliente/pedido/catalogo"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
            .andDo(print());
    }
}
