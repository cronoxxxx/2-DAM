package org.example.mazmorrafx_adriansaavedra.logic;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.example.mazmorrafx_adriansaavedra.model.Door;
import org.example.mazmorrafx_adriansaavedra.model.Dungeon;
import org.example.mazmorrafx_adriansaavedra.model.Room;

public class MazmorraTree {
    public TreeView<String> createTreeView(Dungeon dungeon) {
        TreeItem<String> rootItem = new TreeItem<>("Mazmorra");
        for (Room room : dungeon.getRooms()) {
            TreeItem<String> roomItem = new TreeItem<>("Habitación " + room.getId());
            roomItem.getChildren().add(new TreeItem<>("Descripción: " + room.getDescription()));
            TreeItem<String> doorsItem = new TreeItem<>("Puertas");
            for (Door door : room.getDoors()) {
                doorsItem.getChildren().add(new TreeItem<>(door.getName() + " -> " + door.getDest()));
            }
            roomItem.getChildren().add(doorsItem);
            rootItem.getChildren().add(roomItem);
        }
        return new TreeView<>(rootItem);
    }
}