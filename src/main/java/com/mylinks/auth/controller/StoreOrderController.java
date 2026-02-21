package com.mylinks.auth.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.dto.OrderResponse;
import com.mylinks.auth.service.OrderCancellationService;
import com.mylinks.auth.service.OrderQueryService;
import com.mylinks.auth.service.RefundService;
import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Order;

@RestController
@RequestMapping("/api/stores/{storeId}/orders")
public class StoreOrderController {

    @Autowired
    private OrderQueryService orderQueryService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private OrderCancellationService cancellationService;
    
    @Autowired
    private RefundService refundService;
    
    @GetMapping
    public List<OrderResponse> storeOrders(@PathVariable UUID storeId,
                                           Authentication auth) {

        // 🔐 verify store ownership here
        // store.userId == auth userId

        return orderQueryService.getStoreOrders(storeId);
    }
    
    @PutMapping("/{orderId}/status")
    public void updateStatus(@PathVariable UUID orderId,
                             @RequestParam String status) {

        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(status);
        orderRepository.save(order);
    }
    @PostMapping("/{orderId}/cancel")
    public void cancel(@PathVariable UUID orderId,
                       Authentication auth) {

        UUID userId = userRepository.findByEmail(auth.getName())
                .orElseThrow()
                .getId();

        cancellationService.cancelOrder(orderId, userId);
    }
    
    @PostMapping("/{orderId}/refund")
    public void refund(@PathVariable UUID orderId,
                       @RequestParam String reason) throws Exception {

        refundService.refundOrder(orderId, reason);
    }
}
