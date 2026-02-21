package com.mylinks.auth.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.auth.service.ProductService;
import com.mylinks.users.entity.Product;


@RestController
@RequestMapping("/api/stores/{storeId}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@PathVariable UUID storeId,
                                 @RequestBody Product product) {
        return productService.createProduct(storeId, product);
    }

    @GetMapping
    public List<Product> getProducts(@PathVariable UUID storeId) {
        return productService.getStoreProducts(storeId);
    }
}
