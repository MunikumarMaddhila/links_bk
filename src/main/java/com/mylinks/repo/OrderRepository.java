package com.mylinks.repo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mylinks.auth.dto.DailySalesResponse;
import com.mylinks.users.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserId(UUID userId);
    
    // 👤 User order history
    List<Order> findByUserIdOrderByCreatedAtDesc(UUID userId);

    // 🏪 Store owner order history
    List<Order> findByStoreIdOrderByCreatedAtDesc(UUID storeId);
    
    Optional<Order> findById(UUID id);
    
//    @Query("""
//            SELECT COUNT(o) FROM Order o
//            WHERE o.storeId = :storeId
//        """)
//        long countOrdersByStore(UUID storeId);

//	long countByStoreId(UUID storeId);

//        @Query("""
//            SELECT COALESCE(SUM(o.totalAmount), 0)
//            FROM Order o
//            WHERE o.storeId = :storeId
//              AND o.status = 'PAID'
//        """)
//        BigDecimal totalRevenue(UUID storeId);

//        @Query(
//        		  value = """
//        		    SELECT COALESCE(SUM(total_amount), 0)
//        		    FROM orders
//        		    WHERE store_id = :storeId
//        		      AND status = 'PAID'
//        		      AND DATE(created_at) = CURRENT_DATE
//        		  """,
//        		  nativeQuery = true
//        		)
//        BigDecimal todayRevenue(UUID storeId);

//        long countByStoreIdAndStatus(UUID storeId, String status);
        
//        @Query("""
//        	    SELECT new com.mylinks.dto.DailySalesResponse(
//        FUNCTION('date', o.createdAt),
//        SUM(o.totalAmount)
//    ) FROM Order o
//        	    WHERE o.storeId = :storeId
//        	      AND o.status = 'PAID'
//        	    GROUP BY FUNCTION('date', o.createdAt)
//        	    ORDER BY FUNCTION('date', o.createdAt)
//        	""")
//        	List<DailySalesResponse> dailySales(UUID storeId);
        
//        SELECT new com.mylinks.dto.DailySalesResponse(
//    	        :day,
//    	        COALESCE(SUM(o.totalAmount), 0)
//    	    )
        
//        @Query("""
//        	    select o  
//        	    FROM Order o
//        	    WHERE o.storeId = :storeId
//        	      AND o.status = 'PAID'
//        	      AND o.createdAt >= :start
//        	      AND o.createdAt < :end
//        	""")
//        	DailySalesResponse dailySalesForDay(
//        	        UUID storeId,
//        	        LocalDate day,
//        	        LocalDateTime start,
//        	        LocalDateTime end
//        	);

//		List<Order> findByStoreId(UUID storeId);
}
