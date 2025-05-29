package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Estado;
import com.example.Perfulandia.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LogisticaServiceImpl implements LogisticaService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public List<Estado> obtenerTodos() {
        return estadoRepository.findAll();
    }

    @Override
    public Optional<Estado> obtenerPorId(Integer id_estado) {
        return estadoRepository.findById(id_estado);
    }

    @Override
        public Estado updateEstado(Estado estado) {
            return estadoRepository.save(estado);
        }

    @Override
    public List<Estado> buscarPorEstado(String estadoPedido) {
        return estadoRepository.findAll().stream()
                .filter(e -> e.getEstadoPedido().equalsIgnoreCase(estadoPedido))
                .collect(Collectors.toList());
    }
}
