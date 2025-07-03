package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Factura;
import com.example.Perfulandia.service.FacturaService;
import com.example.Perfulandia.assemblers.FacturaModelAssembler; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;   
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @MockBean
    private FacturaModelAssembler assembler;

    private Factura factura;

    @BeforeEach
    void setUp() {
        factura = new Factura();
        factura.setId_factura(1);
        factura.setId_pedido("Factura de prueba");
        factura.setRut("218003065");
        factura.setFecha_pedido("2023-10-01");
        factura.setCantidad_perfumes("1");
        factura.setValor_total("200.00");
    }

    //OBTENER TODAS LAS FACTURAS
    @Test
    void testGetAllFacturas() throws Exception {
        List<Factura> facturas = Collections.singletonList(factura);
        when(facturaService.listarFacturas()).thenReturn(facturas);

        EntityModel<Factura> entityModel = EntityModel.of(factura);
        when(assembler.toModel(any(Factura.class))).thenReturn(entityModel);

        mockMvc.perform(get("/facturas"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._embedded.facturaList[0].id_pedido").value("Factura de prueba"))
                .andDo(print());
    }

    //OBTENER FACTURA POR ID
    @Test
    void testGetFacturaById() throws Exception {
        when(facturaService.obtenerFacturaPorId(1)).thenReturn(Optional.of(factura));
        EntityModel<Factura> entityModel = EntityModel.of(factura);
        when(assembler.toModel(any(Factura.class))).thenReturn(entityModel);

        mockMvc.perform(get("/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.id_pedido").value("Factura de prueba"))
                .andDo(print());
    }

    //CREAR FACTURA
    @Test
    void testCreateFactura() throws Exception {
        when(facturaService.crearFactura(any(Factura.class))).thenReturn(factura);
        EntityModel<Factura> entityModel = EntityModel.of(factura);
        when(assembler.toModel(any(Factura.class))).thenReturn(entityModel);

        mockMvc.perform(post("/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(factura)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andDo(print());
    }

    //ACTUALIZAR FACTURA
    @Test
    void testUpdateFactura() throws Exception {
        when(facturaService.actualizarFactura(any(Integer.class), any(Factura.class))).thenReturn(factura);
        EntityModel<Factura> entityModel = EntityModel.of(factura);
        when(assembler.toModel(any(Factura.class))).thenReturn(entityModel);

        mockMvc.perform(put("/facturas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(factura)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andDo(print());
    }

    //BORRAR FACTURA
    @Test
    void testDeleteFactura() throws Exception {
        mockMvc.perform(delete("/facturas/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

}
