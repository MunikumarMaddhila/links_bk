package com.mylinks.auth.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.common.ProductCSVRow;
import com.mylinks.repo.ProductRepository;
import com.mylinks.users.entity.Product;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(UUID storeId, Product product) {
        product.setStoreId(storeId);
        return productRepository.save(product);
    }

    public List<Product> getStoreProducts(UUID storeId) {
        return productRepository.findByStoreId(storeId);
    }

	public Product findOrCreate(ProductCSVRow row) {
		// TODO Auto-generated method stub
		return null;
	}
}