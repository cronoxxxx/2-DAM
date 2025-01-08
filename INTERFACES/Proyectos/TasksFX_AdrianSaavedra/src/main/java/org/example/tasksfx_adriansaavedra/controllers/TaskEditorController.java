package org.example.tasksfx_adriansaavedra.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Setter;
import org.controlsfx.control.WorldMapView;
import org.example.tasksfx_adriansaavedra.model.Task;
import javafx.scene.input.MouseEvent;


public class TaskEditorController {

    @FXML private TextField nameField;
    @FXML private TextField descriptionField;
    @FXML private Slider completionSlider;
    @FXML private TextField locationField;
    @FXML private TextField responsibleField;
    @FXML private WorldMapView worldMapView;
    private double latitude;
    private double longitude;
    private Task task;
    @Setter private ObservableList<Task> taskList;
    @Setter private ListView<Task> taskListView;
    @FXML
    public void initialize() {
        // Set up a click listener on the map
        worldMapView.setOnMouseClicked(this::handleMapClick);
    }

    private void handleMapClick(MouseEvent event) {
        latitude = event.getY();
        longitude = event.getX();
        locationField.setText(String.format("%.2f, %.2f", latitude, longitude));
        worldMapView.getLocations().add(new WorldMapView.Location(latitude, longitude));
    }
    public void setTask(Task task) {
        this.task = task;
        if (task != null) {
            nameField.setText(task.getName());
            descriptionField.setText(task.getDescription());
            completionSlider.setValue(task.getCompletionPercentage());
            responsibleField.setText(task.getResponsible());
        }
    }

    @FXML
    private void handleSave() {
        if (task == null) {
            task = new Task(
                    nameField.getText(),
                    descriptionField.getText(),
                    (int) completionSlider.getValue(),
                    locationField.getText(),
                    latitude,
                    longitude
            );
            taskList.add(task);
        } else {
            task.setName(nameField.getText());
            task.setDescription(descriptionField.getText());
            task.setCompletionPercentage((int) completionSlider.getValue());
            task.setResponsible(responsibleField.getText());
            task.setLatitude(latitude);
            task.setLongitude(longitude);
        }

        if (taskListView != null) {
            taskListView.refresh();
        }

        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}