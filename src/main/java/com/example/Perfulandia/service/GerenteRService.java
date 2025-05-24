package com.example.Perfulandia.service;
import com.example.Perfulandia.repository.GerenteRepository;
import com.example.Perfulandia.model.Gerente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
@Service

public class GerenteRService {

    @Autowired
    private GerenteRepository gerenteRepository;


    public List<Gerente> getAllGerentes() {
        return gerenteRepository.findAll();
    }

    
}




