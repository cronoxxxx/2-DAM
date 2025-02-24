package org.example.dao.model;

import lombok.Data;

import java.util.Date;
@Data
public class Order {
    private String id;
    private String customerId;
    private String shopId;
    private Date orderDate;
    private double totalAmount;

    // Getters and setters
}