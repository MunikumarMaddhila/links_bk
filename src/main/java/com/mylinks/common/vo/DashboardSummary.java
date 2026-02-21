package com.mylinks.common.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DashboardSummary {
	private long totalUsers;
    private long totalProducts;
    private long totalVariants;
    private long totalOrders;
    private BigDecimal revenue;
}
