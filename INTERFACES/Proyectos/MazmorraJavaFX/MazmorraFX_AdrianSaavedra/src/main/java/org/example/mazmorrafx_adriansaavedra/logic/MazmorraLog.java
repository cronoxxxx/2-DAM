package org.example.mazmorrafx_adriansaavedra.logic;

import javafx.scene.control.TextArea;

public class MazmorraLog {
    private final TextArea logArea;

    public MazmorraLog(TextArea logArea) {
        this.logArea = logArea;
    }

    public void logMessage(String message) {
        logArea.appendText(message + "\n");
    }
}
