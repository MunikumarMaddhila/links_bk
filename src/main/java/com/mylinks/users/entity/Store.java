package com.mylinks.users.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stores")
@Data
public class Store {

	@Id
    private UUID id;

    private UUID ownerId;
    private String name;

    private String approvalStatus; // PENDING, APPROVED, REJECTED, SUSPENDED
    private UUID approvedBy;
    private LocalDateTime approvedAt;
    private String rejectionReason;
    private String status;
    private Boolean isActive;
    private UUID userId;
}

