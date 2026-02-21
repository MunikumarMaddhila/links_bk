package com.mylinks.admin.controller;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.admin.service.AdminProductService;
import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.users.entity.Product;
import com.mylinks.users.entity.ProductVariant;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminProductController {

    @Autowired
    private AdminProductService productService;

    @PostMapping
    public Product create(@RequestBody Product product, Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return productService.create(product, storeId);
    }

    @PostMapping("/{productId}/variants")
    public ProductVariant addVariant(
            @PathVariable UUID productId,
            @RequestBody ProductVariant variant,
            Authentication auth) {

        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        try {
			return productService.addVariant(productId, variant, storeId);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return variant;
    }

    @PutMapping("/variants/{variantId}/stock")
    public void updateStock(
            @PathVariable UUID variantId,
            @RequestParam int stock,
            Authentication auth) {

        try {
			productService.updateStock(variantId, stock, auth);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

