package com.mylinks.auth.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mylinks.auth.dto.NotificationType;
import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.PaymentRepository;
import com.mylinks.repo.UserRepository;
import com.mylinks.users.entity.Order;
import com.mylinks.users.entity.Payment;
import com.mylinks.users.entity.User;
import com.razorpay.Utils;

@Service
public class PaymentVerificationService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    /**
     * Verify Razorpay payment signature
     */
    @Transactional
    public void verifyPayment(
            UUID orderId,
            String razorpayOrderId,
            String razorpayPaymentId,
            String razorpaySignature,
            String razorpaySecret
    ) {

        // 1️⃣ Fetch order FIRST
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // ✅ COD GUARD (THIS IS WHAT YOU ASKED)
        if ("COD".equalsIgnoreCase(order.getPaymentMode())) {
            throw new RuntimeException("COD orders do not require payment verification");
        }

        // 2️⃣ Verify Razorpay signature (ONLINE ONLY)
        try {
            Map<String, String> data = Map.of(
                    "razorpay_order_id", razorpayOrderId,
                    "razorpay_payment_id", razorpayPaymentId,
                    "razorpay_signature", razorpaySignature
            );

            //boolean isValid = Utils.verifyPaymentSignature(data, razorpaySecret);
            boolean isValid = false;
            if (!isValid) {
                throw new RuntimeException("Invalid Razorpay signature");
            }

            // 3️⃣ Update order
            order.setStatus("PAID");
            order.setPaymentStatus("PAID");
            orderRepository.save(order);

            // 4️⃣ Update payment
            Payment payment = paymentRepository
                    .findByRazorpayOrderId(razorpayOrderId)
                    .orElseThrow(() -> new RuntimeException("Payment record not found"));

            payment.setRazorpayPaymentId(razorpayPaymentId);
            payment.setRazorpaySignature(razorpaySignature);
            payment.setStatus("SUCCESS");

            paymentRepository.save(payment);

            // 5️⃣ Notify user
            User user = userRepository.findById(order.getUserId()).orElseThrow();

            notificationService.notify(
                    NotificationType.PAYMENT_SUCCESS,
                    user.getEmail(),
                    user.getPhone(),
                    Map.of("orderId", order.getId())
            );

        } catch (Exception ex) {
            throw new RuntimeException("Payment verification failed", ex);
        }
    }

}

