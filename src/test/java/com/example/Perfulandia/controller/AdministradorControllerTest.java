package com.example.Perfulandia.controller;

import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.service.ClienteService;
import com.example.Perfulandia.assemblers.ClienteModelAssembler;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.hateoas.EntityModel;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(AdministradorController.class)
public class AdministradorControllerTest {
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



// TEST LISTAR CLIENTES
    @Test
    void listarClientes() throws Exception {
        // Datos del cliente
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setRut("12345678-9");
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setTelefono("987654321");
        cliente.setCorreo("juan@mail.com");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setRol("cliente");

    // Mock de servicio
    when(clienteService.listarClientes()).thenReturn(Collections.singletonList(cliente));

    // Mock del assembler con enlace
    EntityModel<Cliente> clienteModel = EntityModel.of(cliente,
        linkTo(methodOn(AdministradorController.class).obtenerPorId(cliente.getId())).withSelfRel()
    );
    when(assembler.toModel(cliente)).thenReturn(clienteModel);

    // Prueba
    mockMvc.perform(get("/admin/clientes"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/hal+json"))
            .andExpect(jsonPath("$._embedded.clienteList[0].id").value(cliente.getId()))
            .andExpect(jsonPath("$._embedded.clienteList[0].rut").value(cliente.getRut()))
            .andExpect(jsonPath("$._embedded.clienteList[0].nombre").value(cliente.getNombre()))
            .andExpect(jsonPath("$._embedded.clienteList[0].apellido").value(cliente.getApellido()))
            .andExpect(jsonPath("$._embedded.clienteList[0].telefono").value(cliente.getTelefono()))
            .andExpect(jsonPath("$._embedded.clienteList[0].correo").value(cliente.getCorreo()))
            .andExpect(jsonPath("$._embedded.clienteList[0].direccion").value(cliente.getDireccion()))
            .andExpect(jsonPath("$._embedded.clienteList[0].rol").value(cliente.getRol()))
            .andExpect(jsonPath("$._links.self.href").exists());
}



//TEST OBTENER CLIENTES POR ID
    @Test
    void obtenerPorId() throws Exception {
    Cliente cliente = new Cliente();
    cliente.setId(1L);
    cliente.setRut("12345678-9");
    cliente.setNombre("Juan");
    cliente.setApellido("Pérez");
    cliente.setTelefono("987654321");
    cliente.setCorreo("juan@mail.com");
    cliente.setDireccion("Calle Falsa 123");
    cliente.setRol("cliente");

    when(clienteService.obtenerClientePorId(1)).thenReturn(Optional.of(cliente));

    EntityModel<Cliente> clienteModel = EntityModel.of(cliente,
        linkTo(methodOn(AdministradorController.class).obtenerPorId(cliente.getId())).withSelfRel()
    );
    when(assembler.toModel(cliente)).thenReturn(clienteModel);

    mockMvc.perform(get("/admin/clientes/{id}", 1))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/hal+json")) // línea clave
            .andExpect(jsonPath("$.id").value(cliente.getId()))
            .andExpect(jsonPath("$.rut").value(cliente.getRut()))
            .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
            .andExpect(jsonPath("$.apellido").value(cliente.getApellido()))
            .andExpect(jsonPath("$.telefono").value(cliente.getTelefono()))
            .andExpect(jsonPath("$.correo").value(cliente.getCorreo()))
            .andExpect(jsonPath("$.direccion").value(cliente.getDireccion()))
            .andExpect(jsonPath("$.rol").value(cliente.getRol()))
            .andExpect(jsonPath("$._links.self.href").exists());
}





// TEST ACTUALIZAR CLIENTE
    @Test
    void actualizarCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setRut("12345678-9");
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setTelefono("987654321");
        cliente.setCorreo("juan@mail.com");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setRol("cliente");

    when(clienteService.updateCliente(any(Cliente.class))).thenReturn(cliente);
    when(assembler.toModel(cliente)).thenReturn(EntityModel.of(cliente));

    mockMvc.perform(put("/admin/clientes/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cliente)))
            .andDo(print()) // Muestra la respuesta real en consola
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cliente.getId()))
            .andExpect(jsonPath("$.rut").value(cliente.getRut()))
            .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
            .andExpect(jsonPath("$.apellido").value(cliente.getApellido()))
            .andExpect(jsonPath("$.telefono").value(cliente.getTelefono()))
            .andExpect(jsonPath("$.correo").value(cliente.getCorreo()))
            .andExpect(jsonPath("$.direccion").value(cliente.getDireccion()))
            .andExpect(jsonPath("$.rol").value(cliente.getRol()));
            EntityModel<Cliente> modelConLink = EntityModel.of(cliente,
         linkTo(methodOn(AdministradorController.class).obtenerPorId(cliente.getId())).withSelfRel()
    );
    when(clienteService.updateCliente(any(Cliente.class))).thenReturn(cliente);
    when(assembler.toModel(cliente)).thenReturn(modelConLink);
    mockMvc.perform(put("/admin/clientes/{id}", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(cliente)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cliente.getId()))
            .andExpect(jsonPath("$.rut").value(cliente.getRut()))
            .andExpect(jsonPath("$.nombre").value(cliente.getNombre()))
            .andExpect(jsonPath("$.apellido").value(cliente.getApellido()))
            .andExpect(jsonPath("$.telefono").value(cliente.getTelefono()))
            .andExpect(jsonPath("$.correo").value(cliente.getCorreo()))
            .andExpect(jsonPath("$.direccion").value(cliente.getDireccion()))
            .andExpect(jsonPath("$.rol").value(cliente.getRol()))
            .andExpect(jsonPath("$._links.self.href").exists());
}



//TEST ELIMINAR CLIENTE

    @Test
    void eliminarCliente() throws Exception {
        mockMvc.perform(delete("/admin/clientes/{id}", 1))
                .andExpect(status().isNoContent());
    }



}
