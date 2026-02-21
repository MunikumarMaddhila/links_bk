package com.mylinks.auth.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.HmacUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.StorePaymentConfigRepository;
import com.mylinks.users.entity.Order;
import com.mylinks.users.entity.Payment;
import com.mylinks.users.entity.StorePaymentConfig;
import com.razorpay.RazorpayClient;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    @Autowired
    private StorePaymentConfigRepository storePaymentRepo;

    @Autowired
    private OrderRepository orderRepository;

    public Map<String, Object> createPaymentOrder(UUID orderId) throws Exception {

        Order order = orderRepository.findById(orderId).orElseThrow();

        // 🔹 GET STORE PAYMENT CONFIG
        StorePaymentConfig config = storePaymentRepo
                .findByStoreIdAndActiveTrue(order.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store payment not configured"));

        RazorpayClient client =
                new RazorpayClient(config.getKeyId(), config.getKeySecret());

        JSONObject options = new JSONObject();
        options.put("amount", order.getTotalAmount().multiply(BigDecimal.valueOf(100)));
        options.put("currency", "INR");
        options.put("receipt", order.getId().toString());

        com.razorpay.Order rpOrder = client.orders.create(options);

        return Map.of(
            "razorpayOrderId", rpOrder.get("id"),
            "amount", options.get("amount"),
            "currency", "INR",
            "key", config.getKeyId() // 👈 STORE KEY
        );
    }
    
    
    @Transactional
    public void verifyPayment(UUID orderId,
                              String razorpayOrderId,
                              String razorpayPaymentId,
                              String razorpaySignature) throws Exception {

        Order order = orderRepository.findById(orderId).orElseThrow();

        StorePaymentConfig config = storePaymentRepo
                .findByStoreIdAndActiveTrue(order.getStoreId())
                .orElseThrow();

        String payload = razorpayOrderId + "|" + razorpayPaymentId;
        String generatedSignature =
                HmacUtils.hmacSha256Hex(config.getKeySecret(), payload);

        if (!generatedSignature.equals(razorpaySignature)) {
            throw new RuntimeException("Invalid payment signature");
        }

        // mark payment + order PAID (same as before)
    }


	public void refund(UUID orderId, Authentication auth) {
		// TODO Auto-generated method stub
		
	}


	public List<Payment> list(Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

}
