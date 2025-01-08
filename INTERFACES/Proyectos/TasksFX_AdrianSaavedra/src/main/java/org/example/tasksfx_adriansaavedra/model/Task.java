package org.example.tasksfx_adriansaavedra.model;
import lombok.*;

@Data
@AllArgsConstructor
public class Task {
    private String name;
    private String description;
    private int completionPercentage;
    private String responsible;
    private double latitude;
    private double longitude;
}
