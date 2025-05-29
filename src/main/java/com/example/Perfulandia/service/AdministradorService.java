package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface AdministradorService {
    List<Cliente> listarClientes();
    Optional<Cliente> obtenerClientePorId(Integer id);
    Cliente actualizarCliente(Integer id, Cliente cliente);
    void eliminarCliente(Integer id);
}
