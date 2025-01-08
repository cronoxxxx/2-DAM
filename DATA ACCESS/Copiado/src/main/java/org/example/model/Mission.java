package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mission {
    private int id;
    private String name;
    private String description;
    private int tokens;
}
