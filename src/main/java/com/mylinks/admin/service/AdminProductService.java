package com.mylinks.admin.service;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.repo.ProductRepository;
import com.mylinks.repo.ProductVariantRepository;
import com.mylinks.users.entity.Product;
import com.mylinks.users.entity.ProductVariant;

@Service
public class AdminProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository variantRepository;

    public Product create(Product product, UUID storeId) {
        product.setId(UUID.randomUUID());
        product.setStoreId(storeId);
        product.setActive(true);
        return productRepository.save(product);
    }

    public ProductVariant addVariant(
            UUID productId,
            ProductVariant variant,
            UUID storeId) throws AccessDeniedException {

        Product product = productRepository.findById(productId).orElseThrow();

        if (!product.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        variant.setId(UUID.randomUUID());
        variant.setProductId(productId);
        variant.setSku(generateSku(product, variant));

        return variantRepository.save(variant);
    }

    public void updateStock(UUID variantId, int stock, Authentication auth) throws AccessDeniedException {
        ProductVariant variant = variantRepository.findById(variantId).orElseThrow();
        Product product = productRepository.findById(variant.getProductId()).orElseThrow();

        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();

        if (!product.getStoreId().equals(storeId)) {
            throw new AccessDeniedException("Forbidden");
        }

        variant.setStock(stock);
        variantRepository.save(variant);
    }

    private String generateSku(Product product, ProductVariant variant) {
        return product.getName().substring(0, 3).toUpperCase()
                + "-" + variant.getColor()
                + "-" + variant.getSize();
    }
}
