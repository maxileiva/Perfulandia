package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Estado;
import java.util.List;
import java.util.Optional;

public interface LogisticaService {
    List<Estado> obtenerTodos();
    Optional<Estado> obtenerPorId(Integer id_estado);
    Estado updateEstado(Estado estado);
    List<Estado> buscarPorEstado(String estadoPedido);
}