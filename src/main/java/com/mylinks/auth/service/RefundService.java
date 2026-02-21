package com.mylinks.auth.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.repo.OrderItemRepository;
import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.PaymentRepository;
import com.mylinks.repo.ProductRepository;
import com.mylinks.repo.RefundRepository;
import com.mylinks.repo.StorePaymentConfigRepository;
import com.mylinks.users.entity.Order;
import com.mylinks.users.entity.OrderItem;
import com.mylinks.users.entity.Product;
import com.mylinks.users.entity.Refund;
import com.mylinks.users.entity.StorePaymentConfig;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;

import jakarta.transaction.Transactional;

@Service
public class RefundService {

    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StorePaymentConfigRepository storePaymentRepo;

    @Transactional
    public void refundOrder(UUID orderId, String reason) throws Exception {

        Order order = orderRepository.findById(orderId).orElseThrow();

        if (!order.getStatus().equals("PAID")) {
            throw new RuntimeException("Refund not allowed");
        }

        /**
         * CHECK later
         */
//        Payment payment = paymentRepository
//                .findByOrderId(orderId)
//                .orElseThrow();

        StorePaymentConfig config = storePaymentRepo
                .findByStoreIdAndActiveTrue(order.getStoreId())
                .orElseThrow();

        RazorpayClient client =
                new RazorpayClient(config.getKeyId(), config.getKeySecret());

        JSONObject options = new JSONObject();
        options.put("amount", order.getTotalAmount().multiply(BigDecimal.valueOf(100)));

//        client.payments.refund(payment.getRazorpayPaymentId(), options);

        // Save refund record
        Refund refund = new Refund();
        refund.setOrderId(orderId);
//        refund.setPaymentId(payment.getId());
        refund.setAmount(order.getTotalAmount());
        refund.setReason(reason);
        refund.setStatus("SUCCESS");
        refundRepository.save(refund);

        // Restore stock
        for (OrderItem item : orderItemRepository.findByOrderId(orderId)) {
            Product p = productRepository.findById(item.getProductId()).orElseThrow();
            p.setStock(p.getStock() + item.getQuantity());
            productRepository.save(p);
        }

        order.setStatus("REFUNDED");
        orderRepository.save(order);
    }
}
