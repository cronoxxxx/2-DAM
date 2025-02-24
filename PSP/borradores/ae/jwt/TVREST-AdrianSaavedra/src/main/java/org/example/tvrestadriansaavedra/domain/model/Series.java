package org.example.tvrestadriansaavedra.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Series {
    private Long id;
    private String name;
    private List<Chapter> chapters = new ArrayList<>();
}

