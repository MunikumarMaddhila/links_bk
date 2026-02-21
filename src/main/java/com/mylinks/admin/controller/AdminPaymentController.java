package com.mylinks.admin.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.PaymentService;
import com.mylinks.users.entity.Payment;

@RestController
@RequestMapping("/api/admin/payments")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminPaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/{orderId}/refund")
    public void refund(@PathVariable UUID orderId, Authentication auth) {
        paymentService.refund(orderId, auth);
    }

    @GetMapping
    public List<Payment> payments(Authentication auth) {
        return paymentService.list(auth);
    }
}
