package org.example.dao.model;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data

public class Flavor {
    private String id;
    private String name;
    private String category;
    private List<String> ingredients;
    private Map<String, Double> price;
    private boolean inStock;
}