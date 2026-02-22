package com.mylinks.auth.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.auth.dto.NotificationType;
import com.mylinks.repo.CartItemRepository;
import com.mylinks.repo.CartRepository;
import com.mylinks.repo.OrderItemRepository;
import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.ProductRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Cart;
import com.mylinks.users.entity.CartItem;
import com.mylinks.users.entity.Order;
import com.mylinks.users.entity.OrderItem;
import com.mylinks.users.entity.Product;
import com.mylinks.users.entity.User;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    
//    @Autowired
//    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order checkout(UUID userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart empty"));

        List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

        if (items.isEmpty()) {
            throw new RuntimeException("Cart empty");
        }

        // 🔹 Create order
        Order order = new Order();
        order.setUserId(userId);
        order.setStoreId(
            productRepository.findById(items.get(0).getProductId())
                .orElseThrow().getStoreId()
        );
        order.setStatus("PENDING");

        BigDecimal total = BigDecimal.ZERO;
        order = orderRepository.save(order);
/**
 * NOTIFICATION SERVICE
 */
//        notificationService.notify(
//        	    NotificationType.ORDER_PLACED,
//        	    user.getEmail(),
//        	    user.getPhone(),
//        	    Map.of("orderId", order.getId())
//        	);
        
        // 🔹 Create order items
        for (CartItem item : items) {

            Product product = productRepository
                    .findById(item.getProductId())
                    .orElseThrow();

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Out of stock: " + product.getName());
            }

            OrderItem oi = new OrderItem();
            oi.setOrderId(order.getId());
            oi.setProductId(product.getId());
            oi.setProductName(product.getName());
            oi.setQuantity(item.getQuantity());
            oi.setPrice(product.getDiscountPrice() != null
                    ? product.getDiscountPrice()
                    : product.getPrice());

            total = total.add(oi.getPrice().multiply(
                    BigDecimal.valueOf(oi.getQuantity())));

            orderItemRepository.save(oi);
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }
    
    @Transactional
    public Order checkout(UUID userId, String paymentMode) {

        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setUserId(userId);
        order.setPaymentMode(paymentMode);

        if ("COD".equals(paymentMode)) {
            order.setStatus("PLACED");
            order.setPaymentStatus("PENDING");
        } else {
            order.setStatus("PENDING");      // waiting for Razorpay
            order.setPaymentStatus("PENDING");
        }

        orderRepository.save(order);

        
        // 🔔 Notification
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

//        notificationService.notify(
//            NotificationType.ORDER_PLACED,
//            user.getEmail(),
//            user.getPhone(),
//            Map.of(
//                "orderId", order.getId(),
//                "mode", paymentMode
//            )
//        );

        return order;
    }

}
