package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacterDB {
    private int id;
    private String name;
    private String type;
    private int level;
    private int player_id;
}