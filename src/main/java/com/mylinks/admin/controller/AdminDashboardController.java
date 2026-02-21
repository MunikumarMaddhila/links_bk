package com.mylinks.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.admin.service.AdminDashboardService;
import com.mylinks.auth.dto.DailySalesResponse;
import com.mylinks.common.vo.DashboardSummary;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardSummary summary(Authentication auth) {
        return dashboardService.summary(auth);
    }

//    @GetMapping("/daily-sales")
//    public List<DailySalesResponse> daily(Authentication auth) {
//        return dashboardService.dailySales(auth);
//    }
}

