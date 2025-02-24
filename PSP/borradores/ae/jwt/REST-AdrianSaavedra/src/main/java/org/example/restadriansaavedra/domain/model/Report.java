package org.example.restadriansaavedra.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data@AllArgsConstructor
public class Report {
    private Long id;
    private String name;
    private LocalDate date;
    private String securityLevel;
}