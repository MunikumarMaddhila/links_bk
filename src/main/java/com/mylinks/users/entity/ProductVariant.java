package com.mylinks.users.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProductVariant {

    @Id
    private UUID id;

    private UUID storeId;
    private UUID productId;

    private String sku;
    private Double price;
    private Integer stock;
    private String color;
    private String size;
    private String attributes;
}