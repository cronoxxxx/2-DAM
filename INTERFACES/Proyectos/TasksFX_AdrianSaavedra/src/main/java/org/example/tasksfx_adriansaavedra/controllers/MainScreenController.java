package org.example.tasksfx_adriansaavedra.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.tasksfx_adriansaavedra.model.Task;

public class MainScreenController {

    @FXML
    private ListView<Task> taskListView;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tasks.addAll(
                new Task("Task 1", "Description 1", 0, "Location 1", 0, 0),
                new Task("Task 2", "Description 2", 50, "Location 2",  0, 0)
        );
        taskListView.setItems(tasks);
    }

    @FXML
    private void handleNewTask() {
        openTaskEditor(null);
    }

    @FXML
    private void handleEditTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            openTaskEditor(selectedTask);
        }
    }

    @FXML
    private void handleShowStatistics() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tasksfx_adriansaavedra/statistics.fxml"));
            Parent root = loader.load();

            StatisticsController statisticsController = loader.getController();
            statisticsController.setTasks(tasks);  // Pasamos la lista de tareas

            Stage stage = new Stage();
            stage.setTitle("Task Statistics");
            stage.setScene(new Scene(root, 400, 300));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openTaskEditor(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tasksfx_adriansaavedra/task_editor.fxml"));
            Parent root = loader.load();

            TaskEditorController controller = loader.getController();
            controller.setTask(task);
            controller.setTaskList(tasks); // Set observable list
            controller.setTaskListView(taskListView); // Pass ListView reference

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 300));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
