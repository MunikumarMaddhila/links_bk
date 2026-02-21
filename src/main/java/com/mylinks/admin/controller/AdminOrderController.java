package com.mylinks.admin.controller;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mylinks.admin.service.AdminOrderService;
import com.mylinks.auth.service.CustomUserDetails;
import com.mylinks.users.entity.Order;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminOrderController {

    @Autowired
    private AdminOrderService orderService;

    @GetMapping
    public List<Order> list(Authentication auth) {
        UUID storeId = ((CustomUserDetails) auth.getPrincipal()).getStoreId();
        return orderService.listOrders(storeId);
    }

    @PutMapping("/{id}/status")
    public void updateStatus(
            @PathVariable UUID id,
            @RequestParam String status,
            Authentication auth) {

        try {
			orderService.updateStatus(id, status, auth);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @PutMapping("/{id}/cod-paid")
    public void markCodPaid(@PathVariable UUID id, Authentication auth) {
        try {
			orderService.markCodPaid(id, auth);
		} catch (AccessDeniedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
