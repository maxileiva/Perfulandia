package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Gerente;

import com.example.Perfulandia.repository.GerenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service

public class GerenteService {

    @Autowired
    private GerenteRepository gerenteRepository;    

    public Gerente createGerente(@RequestBody Gerente gerente) {
        return gerenteRepository.save(gerente);
    }

    public List<Gerente> getAllGerentes() {
        return gerenteRepository.findAll();
    }

    public Gerente updateGerente(Gerente gerente) {
    return gerenteRepository.save(gerente);
    }   

    public void deleteGerente(@PathVariable Integer id) {
        gerenteRepository.deleteById(id);
    }
}
