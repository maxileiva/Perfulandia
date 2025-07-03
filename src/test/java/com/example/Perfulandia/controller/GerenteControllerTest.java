package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.assemblers.GerenteModelAssembler; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;   
import java.util.List;

import com.example.Perfulandia.service.FacturaService;
import com.example.Perfulandia.assemblers.FacturaModelAssembler; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(GerenteController.class)
public class GerenteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GerenteService gerenteService;

    @MockBean
    private GerenteModelAssembler gerenteModelAssembler;

    @MockBean
    private FacturaService facturaService;

    @MockBean
    private FacturaModelAssembler facturaModelAssembler;

    private Gerente gerente;
    private List<Gerente> gerentes;

    @BeforeEach
    void setUp() {
        gerente = new Gerente();
        gerente.setId(1);
        gerente.setDescripcion("One Million");
        gerente.setConcentracion("EDP");
        gerente.setFecha_lanzamiento("2020");
        gerente.setCantidad("3");
        gerente.setValor(new java.math.BigDecimal("24.990"));
    }

    // Test para crear perfumes
    @Test
    void testCreateGerente() throws Exception {
        when(gerenteService.createGerente(any(Gerente.class))).thenReturn(gerente);
        EntityModel<Gerente> entityModel = EntityModel.of(gerente);
        when(gerenteModelAssembler.toModel(any(Gerente.class))).thenReturn(entityModel);

        mockMvc.perform(post("/api/gerente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(gerente)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.descripcion").value("One Million"))
                .andDo(print());
    }

    //test para ver todos los perfumes
    @Test
    void testGetAllGerentes() throws Exception {
        gerentes = Collections.singletonList(gerente);
        when(gerenteService.getAllGerentes()).thenReturn(gerentes);

        EntityModel<Gerente> entityModel = EntityModel.of(gerente);
        when(gerenteModelAssembler.toModel(any(Gerente.class))).thenReturn(entityModel);

        mockMvc.perform(get("/api/gerente"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._embedded.gerenteList[0].descripcion").value("One Million"))
                .andDo(print());
    }

    //test para actualizar perfumes
    @Test
    void testUpdateGerente() throws Exception {
        when(gerenteService.updateGerente(any(Gerente.class))).thenReturn(gerente);
        EntityModel<Gerente> entityModel = EntityModel.of(gerente);
        when(gerenteModelAssembler.toModel(any(Gerente.class))).thenReturn(entityModel);

        mockMvc.perform(put("/api/gerente/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(gerente)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$.descripcion").value("One Million"))
                .andDo(print());
    }

    // Test para eliminar perfumes
    @Test
    void testDeleteGerente() throws Exception {
        mockMvc.perform(delete("/api/gerente/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
