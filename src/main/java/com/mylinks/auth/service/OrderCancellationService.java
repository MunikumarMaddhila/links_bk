package com.mylinks.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.OrderRepository;
import com.mylinks.users.entity.Order;

@Service
public class OrderCancellationService {

    @Autowired
    private OrderRepository orderRepository;

    public void cancelOrder(UUID orderId, UUID userId) {

        Order order = orderRepository.findById(orderId).orElseThrow();

        if (!order.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Order cannot be cancelled");
        }

        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
