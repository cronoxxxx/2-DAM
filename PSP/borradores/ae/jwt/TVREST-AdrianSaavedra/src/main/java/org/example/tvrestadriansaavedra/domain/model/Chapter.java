package org.example.tvrestadriansaavedra.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chapter {
    private Long id;
    private String name;
    private boolean watched;
}

