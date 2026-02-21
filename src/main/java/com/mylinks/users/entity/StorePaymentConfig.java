package com.mylinks.users.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "store_payment_configs")
@Data
public class StorePaymentConfig {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "store_id")
    private UUID storeId;

    private String provider;
    private String keyId;
    private String keySecret;
    private boolean active;
}
