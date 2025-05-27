package com.example.Perfulandia.service;

import com.example.Perfulandia.model.Invoice;
import com.example.Perfulandia.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
        return invoiceRepository.findById(id).map(invoice -> {
            invoice.setCustomerName(updatedInvoice.getCustomerName());
            invoice.setTotalAmount(updatedInvoice.getTotalAmount());
            invoice.setDate(updatedInvoice.getDate());
            return invoiceRepository.save(invoice);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException("Factura no encontrada");
        }
        invoiceRepository.deleteById(id);
    }
}

