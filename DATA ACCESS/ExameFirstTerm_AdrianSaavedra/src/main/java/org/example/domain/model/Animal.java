package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Animal {
    private int animal_id;
    private String name;
    private String species;
    private int age;
    private int habitat_id;


}
