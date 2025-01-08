package org.example.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private int id;
    private String name;
    private String email;
    private int tokens;
    private List<Characters> characters;
}
