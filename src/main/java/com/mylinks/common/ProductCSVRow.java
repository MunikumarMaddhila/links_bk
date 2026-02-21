package com.mylinks.common;

import lombok.Data;

@Data
public class ProductCSVRow {
	private String productName;
    private String sku;
    private Double price;
    private Integer stock;
    
    private String color;
    private String size;
}
