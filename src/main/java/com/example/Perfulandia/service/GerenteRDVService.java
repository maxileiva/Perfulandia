package com.example.Perfulandia.service;
import com.example.Perfulandia.repository.GerenteRDVRepository;
import com.example.Perfulandia.model.DetallePedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class GerenteRDVService {

    @Autowired
    private GerenteRDVRepository gerenteRDVRepository;


    public List<DetallePedido> getAllDetallePedido() {
        return gerenteRDVRepository.findAll();
    }

}
