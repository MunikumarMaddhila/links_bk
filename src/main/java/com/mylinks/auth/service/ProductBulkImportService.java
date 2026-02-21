package com.mylinks.auth.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.mylinks.common.ProductCSVRow;
import com.mylinks.repo.ProductVariantRepository;
import com.mylinks.users.entity.Product;
import com.mylinks.users.entity.ProductVariant;

import jakarta.transaction.Transactional;

public class ProductBulkImportService {
	
	@Autowired
	private ProductVariantRepository variantRepository;

	@Autowired
	private ProductService productService;
	
	@Transactional
	public void importProducts(MultipartFile file, UUID storeId) {

//	    List<ProductCSVRow> rows = csvParser.parse(file);
//
//	    for (ProductCSVRow row : rows) {
//
//	        Product product = productService.findOrCreate(row);
//
//	        ProductVariant variant = new ProductVariant();
//	        variant.setSku(row.getSku());
//	        variant.setPrice(row.getPrice());
//	        variant.setStock(row.getStock());
//	        variant.setAttributes(Map.of(
//	            "size", row.getSize(),
//	            "color", row.getColor()
//	        ));
//
//	        variantRepository.save(variant);
//	    }
	}

}
