package org.example.dao.model;

import lombok.Data;

import java.util.List;
@Data

public class Customer {
    private String id;
    private String name;
    private String email;
    private List<String> favoriteIceCreamTypes;
    private int loyaltyPoints;
    private String phoneNumber;

    // Getters and setters
}