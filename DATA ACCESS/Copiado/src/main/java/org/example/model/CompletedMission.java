package org.example.model;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletedMission {
    private int mission_ID;
    private int character_ID;
    private LocalDate completion_Date;

    public String toFileString(int idPlayer) {
        return idPlayer + ";" + character_ID + ";" + mission_ID + ";" + completion_Date + "\n";
    }
}
