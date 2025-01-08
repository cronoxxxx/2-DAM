package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Battle {
    private int id;
    private String bname;
    private String faction_one;
    private String faction_two;
    private String bplace;
    private LocalDate bdate;
    private int id_spy;
}
