package com.example.Perfulandia.controller;
import com.example.Perfulandia.model.Cliente;
import com.example.Perfulandia.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/cliente")



public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.createCliente(cliente);
    }

    @GetMapping("{id}")
    public Cliente buscaCliente(@PathVariable Integer id) {
        return clienteService.getClienteId(id);
    }

    @PutMapping("{id}")
    public Cliente actualizarCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return clienteService.updateCliente(cliente);
    }

}
