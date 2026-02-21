package com.mylinks.auth.service;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mylinks.repo.InvoiceRepository;
import com.mylinks.repo.OrderItemRepository;
import com.mylinks.repo.OrderRepository;
import com.mylinks.users.entity.Invoice;
import com.mylinks.users.entity.Order;
import com.mylinks.users.entity.OrderItem;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Invoice generateInvoice(UUID orderId) throws Exception {

        Order order = orderRepository.findById(orderId).orElseThrow();

        // Avoid duplicate invoice
        invoiceRepository.findByOrderId(orderId)
                .ifPresent(i -> { throw new RuntimeException("Invoice already exists"); });

        String invoiceNo = "INV-" + System.currentTimeMillis();
        String filePath = "invoices/" + invoiceNo + ".pdf";

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Header
        document.add(new Paragraph("INVOICE"));
        document.add(new Paragraph("Invoice No: " + invoiceNo));
        document.add(new Paragraph("Date: " + LocalDate.now()));
        document.add(new Paragraph("Order ID: " + orderId));
        document.add(new Paragraph(" "));

        // Items table
        PdfPTable table = new PdfPTable(4);
        table.addCell("Product");
        table.addCell("Qty");
        table.addCell("Price");
        table.addCell("Total");

        BigDecimal grandTotal = BigDecimal.ZERO;

        for (OrderItem item : orderItemRepository.findByOrderId(orderId)) {

            BigDecimal total = item.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            table.addCell(item.getProductName());
            table.addCell(String.valueOf(item.getQuantity()));
            table.addCell(item.getPrice().toString());
            table.addCell(total.toString());

            grandTotal = grandTotal.add(total);
        }

        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Grand Total: ₹" + grandTotal));

        document.close();

        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId);
        invoice.setInvoiceNumber(invoiceNo);
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setPdfPath(filePath);

        return invoiceRepository.save(invoice);
    }
}

