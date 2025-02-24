package org.example.dao.model;

import lombok.Data;

@Data
public class Shop {
    private String id;
    private String name;
    private Address address;
    private String contactNumber;
    private double averageRating;

}