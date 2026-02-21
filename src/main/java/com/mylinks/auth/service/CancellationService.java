package com.mylinks.auth.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.mylinks.repo.OrderRepository;
import com.mylinks.users.entity.Order;

public class CancellationService {
	
	@Autowired
	private OrderRepository orderRepository;

	public void cancelOrder(UUID orderId) {

	    Order order = orderRepository.findById(orderId).orElseThrow();

	    if ("DELIVERED".equals(order.getStatus())) {
	        throw new RuntimeException("Delivered orders cannot be cancelled");
	    }

	    order.setStatus("CANCELLED");
	    orderRepository.save(order);
	}
}
