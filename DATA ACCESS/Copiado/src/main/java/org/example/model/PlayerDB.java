package org.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDB {
    private int player_id;
    private String name;
    private String email;
    private int tokens;
}
