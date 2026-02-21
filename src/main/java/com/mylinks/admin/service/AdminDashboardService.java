package com.mylinks.admin.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.mylinks.auth.dto.DailySalesResponse;
import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.common.vo.DashboardSummary;
import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.ProductRepository;

@Service
public class AdminDashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public DashboardSummary summary(Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();

        DashboardSummary summary = new DashboardSummary();
        summary.setTotalOrders(0);//
        summary.setTotalProducts(productRepository.countByStoreId(storeId));
        summary.setRevenue(null);//orderRepository.totalRevenue(storeId)

        return summary;
    }

    public List<DailySalesResponse> dailySales(Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return null;//orderRepository.dailySales(storeId);
    }
}
