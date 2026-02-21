package com.mylinks.auth.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class StoreDashboardResponse {

    private long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal todayRevenue;
    private long pendingOrders;

    private List<TopProductResponse> topProducts;
    private List<DailySalesResponse> dailySales;
}

