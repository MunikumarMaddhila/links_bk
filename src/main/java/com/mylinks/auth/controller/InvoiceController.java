package com.mylinks.auth.controller;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.InvoiceService;
import com.mylinks.repo.InvoiceRepository;
import com.mylinks.users.entity.Invoice;



@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping("/{orderId}")
    public Invoice generate(@PathVariable UUID orderId) throws Exception {
        return invoiceService.generateInvoice(orderId);
    }

    @GetMapping("/{orderId}/download")
    public ResponseEntity<Resource> download(@PathVariable UUID orderId) throws Exception {

        Invoice invoice = invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Path path = Paths.get(invoice.getPdfPath());
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + path.getFileName())
                .body(resource);
    }
}
