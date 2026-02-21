package com.mylinks.auth.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.jwt.SecurityUtil;
import com.mylinks.auth.service.CheckoutService;
import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.users.entity.Order;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;
    
    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping
    public Order checkout(Authentication auth) {

    	UUID userId = securityUtil.getCurrentUserId(auth);
//    	// 🔹 STEP 1: get email from JWT
//        String email = auth.getName();
//
//        // 🔹 STEP 2: get userId from DB
//        UUID userId = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"))
//                .getId();
        return checkoutService.checkout(userId);
    }
    
    @PostMapping("/checkout")
    public Order checkout(
            @RequestParam String paymentMode,
            Authentication auth
    ) {
        UUID userId = ((CustomUserDetails) auth.getPrincipal()).getUserId();
        return checkoutService.checkout(userId, paymentMode);
    }
}
