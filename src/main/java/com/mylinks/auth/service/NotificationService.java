//package com.mylinks.auth.service;
//
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mylinks.auth.dto.NotificationType;
//
//@Service
//public class NotificationService {
//
//    @Autowired
//    private EmailService emailService;
//
//    @Autowired
//    private WhatsAppService whatsAppService;
//
//    public void notify(NotificationType type,
//                       String email,
//                       String phone,
//                       Map<String, Object> data) {
//
//        switch (type) {
//
//            case ORDER_PLACED -> {
//                emailService.send(
//                    email,
//                    "Order Placed Successfully",
//                    "Your order " + data.get("orderId") + " has been placed."
//                );
//            }
//
//            case PAYMENT_SUCCESS -> {
//                emailService.send(
//                    email,
//                    "Payment Successful",
//                    "Payment received for order " + data.get("orderId")
//                );
//                whatsAppService.send(
//                    phone,
//                    "Payment successful for Order " + data.get("orderId")
//                );
//            }
//
//            case INVOICE_GENERATED -> {
//                emailService.send(
//                    email,
//                    "Invoice Ready",
//                    "Your invoice for order " + data.get("orderId") + " is ready."
//                );
//            }
//
//            case REFUND_COMPLETED -> {
//                emailService.send(
//                    email,
//                    "Refund Processed",
//                    "Refund completed for order " + data.get("orderId")
//                );
//            }
//        }
//    }
//    
//    public void notifyAdmins(String type, Map<String, Object> data) {
//        // TEMP: log only or notify super admins later
////        log.info("Admin notification [{}] {}", type, data);
//    }
//}
