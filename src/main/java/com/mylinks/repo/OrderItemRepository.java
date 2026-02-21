package com.mylinks.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mylinks.auth.dto.DailySalesResponse;
import com.mylinks.auth.dto.TopProductResponse;
import com.mylinks.users.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    List<OrderItem> findByOrderId(UUID orderId);
    
    
//    @Query("""
//    	    SELECT new com.mylinks.dashboard.TopProductResponse(
//    	        oi.productName,
//    	        SUM(oi.quantity)
//    	    )
//    	    FROM OrderItem oi
//    	    JOIN Order o ON oi.orderId = o.id
//    	    WHERE o.storeId = :storeId
//    	      AND o.status = 'PAID'
//    	    GROUP BY oi.productName
//    	    ORDER BY SUM(oi.quantity) DESC
//    	""")
//    	List<TopProductResponse> topProducts(UUID storeId);
//    
//
//    @Query("""
//    	    SELECT new com.mylinks.dashboard.DailySalesResponse(
//    	        DATE(o.createdAt),
//    	        SUM(o.totalAmount)
//    	    )
//    	    FROM Order o
//    	    WHERE o.storeId = :storeId
//    	      AND o.status = 'PAID'
//    	    GROUP BY DATE(o.createdAt)
//    	    ORDER BY DATE(o.createdAt)
//    	""")
//    	List<DailySalesResponse> dailySales(UUID storeId);
}