package com.mylinks.auth.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.dto.OrderResponse;
import com.mylinks.auth.service.OrderQueryService;
import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Order;

@RestController
@RequestMapping("/api/orders")
public class UserOrderController {

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/my")
    public List<OrderResponse> myOrders(Authentication auth) {

        UUID userId = userRepository.findByEmail(auth.getName())
                .orElseThrow()
                .getId();

        return orderQueryService.getUserOrders(userId);
    }
    
    @PutMapping("/{id}/deliver")
    @PreAuthorize("hasAuthority('ORDER_MANAGE')")
    public void markDelivered(@PathVariable UUID id) {

        Order order = orderRepository.findById(id).orElseThrow();

        if (!"COD".equals(order.getPaymentMode())) {
            throw new RuntimeException("Only COD orders allowed");
        }

        order.setStatus("DELIVERED");
        orderRepository.save(order);
    }
    
    @PutMapping("/orders/{id}/cod-paid")
    @PreAuthorize("hasAuthority('ORDER_MANAGE')")
    public void markCodPaid(@PathVariable UUID id) {

        Order order = orderRepository.findById(id).orElseThrow();

        order.setPaymentStatus("PAID");
        order.setStatus("PAID");

        orderRepository.save(order);
    }


}
