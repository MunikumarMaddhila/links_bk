package com.mylinks.users.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    private UUID id;

    private UUID userId;
    private UUID storeId;

    private BigDecimal totalAmount;

    private String status;        // PLACED, PAID, SHIPPED, DELIVERED, CANCELLED
    private String paymentMode;   // ONLINE, COD
    private String paymentStatus; // PENDING, PAID

    private LocalDateTime createdAt;
}
