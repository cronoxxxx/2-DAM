package org.example.mazmorrafx_adriansaavedra;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.mazmorrafx_adriansaavedra.logic.*;
import org.example.mazmorrafx_adriansaavedra.model.*;

import java.util.Map;
import java.util.HashMap;

public class HelloController {
    @FXML public Button northButton;
    @FXML public Button westButton;
    @FXML public Button eastButton;
    @FXML public Button southButton;
    @FXML private Label descriptionLabel;
    @FXML private TreeView<String> treeView;
    @FXML private TextArea logArea;

    private MazmorraLoad mazmorraLoad;
    private MazmorraTree mazmorraTree;
    private MazmorraMove mazmorraMove;
    private MazmorraLog mazmorraLog;
    private Dungeon dungeon;
    private Room currentRoom;

    @FXML
    public void initialize() {
        mazmorraLoad = new MazmorraLoad();
        mazmorraTree = new MazmorraTree();
        Map<String, Button> directionButtons = new HashMap<>();
        directionButtons.put("Norte", northButton);
        directionButtons.put("Sur", southButton);
        directionButtons.put("Este", eastButton);
        directionButtons.put("Oeste", westButton);
        mazmorraMove = new MazmorraMove(directionButtons);
        mazmorraLog = new MazmorraLog(logArea);
    }

    @FXML
    public void loadXML() {
        mazmorraLoad.loadXMLFile();
        dungeon = mazmorraLoad.getDungeon();
        if (dungeon != null && !dungeon.getRooms().isEmpty()) {
            treeView.setRoot(mazmorraTree.createTreeView(dungeon).getRoot());
            currentRoom = dungeon.getRooms().getFirst();
            updateRoom(currentRoom);
            mazmorraLog.logMessage("Comienza tu aventura en la habitación " + currentRoom.getId());
        }
    }

    @FXML
    public void onNorthButtonClick() {
        move("Norte");
    }

    @FXML
    public void onSouthButtonClick() {
        move("Sur");
    }

    @FXML
    public void onEastButtonClick() {
        move("Este");
    }

    @FXML
    public void onWestButtonClick() {
        move("Oeste");
    }

    private void move(String direction) {
        for (Door door : currentRoom.getDoors()) {
            if (door.getName().equals(direction)) {
                String destId = door.getDest();
                dungeon.getRooms().stream()
                        .filter(r -> r.getId().equals(destId))
                        .findFirst()
                        .ifPresent(this::updateRoom);
                break;
            }
        }
    }

    private void updateRoom(Room room) {
        currentRoom = room;
        descriptionLabel.setText(room.getDescription());
        mazmorraLog.logMessage("Has ido a: " + room.getId());
        updateButtons();
    }

    private void updateButtons() {
        northButton.setDisable(true);
        southButton.setDisable(true);
        eastButton.setDisable(true);
        westButton.setDisable(true);
        for (Door door : currentRoom.getDoors()) {
            switch (door.getName()) {
                case "Norte" -> northButton.setDisable(false);
                case "Sur" -> southButton.setDisable(false);
                case "Este" -> eastButton.setDisable(false);
                case "Oeste" -> westButton.setDisable(false);
            }
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }


}