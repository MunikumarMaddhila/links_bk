package com.mylinks.common.vo;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mylinks.repo.OrderRepository;
import com.mylinks.repo.ProductRepository;
import com.mylinks.repo.ProductVariantRepository;
import com.mylinks.repo.UserRepository;

@Service
public class DashboardService {

    private UserRepository userRepo;
    private ProductRepository productRepo;
    private ProductVariantRepository variantRepo;
    private OrderRepository orderRepo;

    public DashboardSummary getSummary(UUID storeId) {
        DashboardSummary d = new DashboardSummary();
        d.setTotalUsers(userRepo.countByStoreId(storeId));
        d.setTotalProducts(productRepo.countByStoreId(storeId));
        d.setTotalVariants(variantRepo.countByStoreId(storeId));
        d.setTotalOrders(0);//orderRepo.countByStoreId(storeId)
        return d;
    }
}