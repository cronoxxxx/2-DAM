package org.example.model.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameError {
    private final String message;
    private final LocalDateTime date;

    public GameError(String message) {
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
