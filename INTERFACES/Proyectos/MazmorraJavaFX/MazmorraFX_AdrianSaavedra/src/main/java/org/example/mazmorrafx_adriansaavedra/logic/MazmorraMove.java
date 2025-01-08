package org.example.mazmorrafx_adriansaavedra.logic;

import javafx.scene.control.Button;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;


@Setter@Getter
public class MazmorraMove {
    private final Map<String, Button> directionButtons;

    public MazmorraMove( Map<String, Button> directionButtons) {
        this.directionButtons = directionButtons;
        disableAllButtons();
    }

    public void disableAllButtons() {
        directionButtons.values().forEach(button -> button.setDisable(true));
    }
}
