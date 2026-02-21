package com.mylinks.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.auth.dto.OrderItemResponse;
import com.mylinks.auth.dto.OrderResponse;
import com.mylinks.repo.OrderItemRepository;
import com.mylinks.repo.OrderRepository;
import com.mylinks.users.entity.Order;

@Service
public class OrderQueryService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderResponse> getUserOrders(UUID userId) {

        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapOrder)
                .toList();
    }

    public List<OrderResponse> getStoreOrders(UUID storeId) {

        return orderRepository.findByStoreIdOrderByCreatedAtDesc(storeId)
                .stream()
                .map(this::mapOrder)
                .toList();
    }

    private OrderResponse mapOrder(Order order) {

        List<OrderItemResponse> items =
                orderItemRepository.findByOrderId(order.getId())
                        .stream()
                        .map(item -> {
                            OrderItemResponse r = new OrderItemResponse();
                            r.setProductName(item.getProductName());
                            r.setQuantity(item.getQuantity());
                            r.setPrice(item.getPrice());
                            return r;
                        })
                        .toList();

        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(items);

        return response;
    }
}
