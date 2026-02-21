package com.mylinks.auth.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemResponse {

	private String productName;
	private int quantity;
	private BigDecimal price;
}
