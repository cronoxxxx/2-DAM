package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Player {
    private int id;
    private String name;
    private String team;
    private String country;
}
