package org.example.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Payment {
    private Integer amount;
    private LocalDate date;
}
