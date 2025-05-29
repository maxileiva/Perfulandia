package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Factura;

import java.util.List;
import java.util.Optional;

public interface FacturaService {
    Factura crearFactura(Factura factura);
    Factura actualizarFactura(Integer id, Factura factura);
    void eliminarFactura(Integer id);
    Optional<Factura> obtenerFacturaPorId(Integer id);
    List<Factura> listarFacturas();
}
