package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Pedido;
import com.example.Perfulandia.service.GerenteRVService;
import com.example.Perfulandia.assemblers.GerenteRVModelAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(GerenteRVController.class)
public class GerenteRVControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GerenteRVService gerenteRVService;

    @MockBean
    private GerenteRVModelAssembler assembler;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId_pedido(1);
        pedido.setRutc("12345678-9");
        pedido.setFecha_pedido("2025-07-01");
        pedido.setNeto("10000");
        pedido.setIva("1900");
        pedido.setTotal(new BigDecimal("11900"));
    }

    @Test
    void testGetAllPedido() throws Exception {
        List<Pedido> pedidos = Collections.singletonList(pedido);
        when(gerenteRVService.getAllPedido()).thenReturn(pedidos);

        EntityModel<Pedido> entityModel = EntityModel.of(pedido);
        when(assembler.toModel(any(Pedido.class))).thenReturn(entityModel);

        mockMvc.perform(get("/api/gerente/reporte/pedido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.pedidoList[0].rutc").value("12345678-9"))
                .andExpect(jsonPath("$._embedded.pedidoList[0].fecha_pedido").value("2025-07-01"))
                .andDo(print());
    }
}
