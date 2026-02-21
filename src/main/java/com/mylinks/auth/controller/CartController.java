package com.mylinks.auth.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.jwt.SecurityUtil;
import com.mylinks.auth.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private SecurityUtil securityUtil;
    
    @PostMapping("/add")
    public void addToCart(@RequestParam UUID productId,
                          @RequestParam int qty,
                          Authentication auth) {

    	UUID userId = securityUtil.getCurrentUserId(auth);
//    	// 🔹 STEP 1: get email from JWT
//        String email = auth.getName();
//
//        // 🔹 STEP 2: get userId from DB
//        UUID userId = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"))
//                .getId();
        cartService.addToCart(userId, productId, qty, BigDecimal.ZERO);
    }
}
