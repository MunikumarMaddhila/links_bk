package com.mylinks.admin.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.auth.service.PaymentService;
import com.mylinks.repo.OrderRepository;
import com.mylinks.users.entity.Order;

@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;

    public List<Order> listOrders(UUID storeId) {
        return null;//orderRepository.findByStoreId(storeId);
    }

    public void updateStatus(UUID orderId, String status, Authentication auth) throws AccessDeniedException {
        Order order = orderRepository.findById(orderId).orElseThrow();
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();

        if (!order.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        order.setStatus(status);
        orderRepository.save(order);
    }

    public void markCodPaid(UUID orderId, Authentication auth) throws AccessDeniedException {
        Order order = orderRepository.findById(orderId).orElseThrow();
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();

        if (!order.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        if (!"COD".equals(order.getPaymentMode())) {
            throw new IllegalStateException("Not a COD order");
        }

        order.setStatus("PAID");
        orderRepository.save(order);
    }
}

