package org.example.tasksfx_adriansaavedra.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/tasksfx_adriansaavedra/main_screen.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login failed");
            alert.setContentText("Please enter a username and password.");
            alert.showAndWait();
        }
    }
}
