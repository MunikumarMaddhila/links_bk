package com.mylinks.auth.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylinks.auth.dto.DailySalesResponse;
import com.mylinks.auth.dto.StoreDashboardResponse;
import com.mylinks.repo.OrderItemRepository;
import com.mylinks.repo.OrderRepository;

@Service
public class StoreDashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public StoreDashboardResponse getDashboard(UUID storeId) {

    	LocalDate today = LocalDate.now();

    	LocalDateTime startOfDay = today.atStartOfDay();
    	LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

    	
    	
        StoreDashboardResponse res = new StoreDashboardResponse();

//        res.setTotalOrders(orderRepository.countOrdersByStore(storeId));
//        res.setTotalRevenue(orderRepository.totalRevenue(storeId));
//        res.setTodayRevenue(orderRepository.todayRevenue(storeId, startOfDay, endOfDay));
        //res.setPendingOrders(
           // orderRepository.countByStoreIdAndStatus(storeId, "PENDING")
//        );

//        res.setTopProducts(orderItemRepository.topProducts(storeId));
//        res.setDailySales(orderItemRepository.dailySales(storeId));

        return res;
    }
    
    public List<DailySalesResponse> dailySales(UUID storeId, int days) {

        List<DailySalesResponse> result = new ArrayList<>();

        LocalDate today = LocalDate.now();

        for (int i = days - 1; i >= 0; i--) {

            LocalDate day = today.minusDays(i);
            LocalDateTime start = day.atStartOfDay();
            LocalDateTime end = day.plusDays(1).atStartOfDay();

            DailySalesResponse res = null;
//                    orderRepository.dailySalesForDay(
//                            storeId, day, start, end);

            result.add(res);
        }

        return result;
    }

}
