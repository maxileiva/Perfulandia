package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.Gerente;
import com.example.Perfulandia.service.GerenteService;
import com.example.Perfulandia.assemblers.GerenteModelAssembler; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;   
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import com.example.Perfulandia.service.GerenteRService;
import com.example.Perfulandia.assemblers.GerenteRModelAssembler;


@WebMvcTest(GerenteRController.class)
public class GerenteRControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GerenteRService gerenteRService;

    @MockBean
    private GerenteRModelAssembler gerenteRModelAssembler;

    @MockBean
    private GerenteService gerenteService;

    @MockBean
    private GerenteModelAssembler gerenteModelAssembler;

    private Gerente gerente;

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

    // Test para obtener todos los gerentes para reporte
    @Test
    void testGetAllGerentes() throws Exception {
        List<Gerente> gerentes = Collections.singletonList(gerente);
        when(gerenteRService.getAllGerentes()).thenReturn(gerentes);

        EntityModel<Gerente> entityModel = EntityModel.of(gerente);
        when(gerenteRModelAssembler.toModel(any(Gerente.class))).thenReturn(entityModel);

        mockMvc.perform(get("/api/gerente/reporte/stock"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(jsonPath("$._embedded.gerenteList[0].descripcion").value("One Million"))
                .andDo(print());
    }


	

}
