package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Factura;
import com.example.Perfulandia.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public Factura crearFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public Factura actualizarFactura(Integer id, Factura nuevaFactura) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        factura.setId_pedido(nuevaFactura.getId_pedido());
        factura.setRut(nuevaFactura.getRut());
        factura.setFecha_pedido(nuevaFactura.getFecha_pedido());
        factura.setCantidad_perfumes(nuevaFactura.getCantidad_perfumes());
        factura.setValor_total(nuevaFactura.getValor_total());

        return facturaRepository.save(factura);
    }

    @Override
    public void eliminarFactura(Integer id) {
        facturaRepository.deleteById(id);
    }

    @Override
    public Optional<Factura> obtenerFacturaPorId(Integer id) {
        return facturaRepository.findById(id);
    }

    @Override
    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }
}
