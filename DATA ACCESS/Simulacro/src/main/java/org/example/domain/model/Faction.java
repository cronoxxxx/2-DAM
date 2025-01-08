package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@AllArgsConstructor
@Data
public class Faction {
    private String fname;
    private String contact;
    private String planet;
    private int number_controlled_systems;
    private LocalDate date_last_purchase;
}
