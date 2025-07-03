package com.example.Perfulandia.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.service.ClienteService;
import com.example.Perfulandia.assemblers.ClienteModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private ClienteModelAssembler assembler;

    private ObjectMapper objectMapper;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setRut("123456789");
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setTelefono("987654321");
        cliente.setCorreo("asd@gmail.com");
        cliente.setDireccion("Calle Falsa 123");      
        cliente.setRol("1");
    } 

    //Test Crear Cliente
    @Test
    void createCliente() throws Exception {
        when(clienteService.createCliente(any(Cliente.class))).thenReturn(cliente);
        when(assembler.toModel(any(Cliente.class))).thenReturn(EntityModel.of(cliente));

        mockMvc.perform(post("/api/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.rut").value(cliente.getRut()))
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.apellido").value(cliente.getApellido()))
                .andExpect(jsonPath("$.telefono").value(cliente.getTelefono()))
                .andExpect(jsonPath("$.correo").value(cliente.getCorreo()))
                .andExpect(jsonPath("$.direccion").value(cliente.getDireccion()))
                .andExpect(jsonPath("$.rol").value(cliente.getRol()));
    }

    // Test Buscar Cliente por ID
    @Test
    void buscaCliente() throws Exception {
        when(clienteService.findById(1)).thenReturn(Optional.of(cliente));
        when(assembler.toModel(any(Cliente.class))).thenReturn(EntityModel.of(cliente));

        mockMvc.perform(get("/api/cliente/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.rut").value(cliente.getRut()))
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.apellido").value(cliente.getApellido()))
                .andExpect(jsonPath("$.telefono").value(cliente.getTelefono()))
                .andExpect(jsonPath("$.correo").value(cliente.getCorreo()))
                .andExpect(jsonPath("$.direccion").value(cliente.getDireccion()))
                .andExpect(jsonPath("$.rol").value(cliente.getRol()));
    }

    // Test Actualizar Cliente
    @Test
    void actualizarCliente() throws Exception {
        cliente.setId(1L);
        when(clienteService.updateCliente(any(Cliente.class))).thenReturn(cliente);
        when(assembler.toModel(any(Cliente.class))).thenReturn(EntityModel.of(cliente));

        mockMvc.perform(put("/api/cliente/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.rut").value(cliente.getRut()))
                .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
                .andExpect(jsonPath("$.apellido").value(cliente.getApellido()))
                .andExpect(jsonPath("$.telefono").value(cliente.getTelefono()))
                .andExpect(jsonPath("$.correo").value(cliente.getCorreo()))
                .andExpect(jsonPath("$.direccion").value(cliente.getDireccion()))
                .andExpect(jsonPath("$.rol").value(cliente.getRol()));
    }




}
