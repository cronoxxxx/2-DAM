package org.example.calculatorfxadriansaavedra;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class HelloController {

    @FXML
    private TextField textfieldResult;

    public void initialize() {
        textfieldResult.setEditable(false);


    }

    @FXML private void addValue(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String value = button.getText();
        String text = textfieldResult.getText();
        textfieldResult.setText(text + value);
    }

    @FXML
    private void makeOperation() {
        String text = textfieldResult.getText();
        text = text.replace('x', '*');

        StringBuilder sb = new StringBuilder();
        boolean lastWasDigit = false;
        for (char c : text.toCharArray()) {
            if (c == '%') {
                if (lastWasDigit) {
                    sb.append("/100");
                } else {
                    sb.append("%");
                }
            } else {
                sb.append(c);
                lastWasDigit = Character.isDigit(c);
            }
        }
        text = sb.toString();

        try {
            Expression expression = new ExpressionBuilder(text).build();
            double result = expression.evaluate();
            textfieldResult.setText(String.valueOf(result));
        } catch (IllegalArgumentException | ArithmeticException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error en la operación");
            alert.setContentText("Expresión inválida: " + e.getMessage());
            alert.show();
        }
    }

    @FXML
    private void cleanScreen() {
        textfieldResult.setText("");
    }

    @FXML
    private void deleteValue() {
        String currentText = textfieldResult.getText();
        if (!currentText.isEmpty()) {
            String newText = currentText.substring(0, currentText.length() - 1);
            textfieldResult.setText(newText);
        }
    }

    @FXML
    private void addOperationParenthesis() {
        String currentText = textfieldResult.getText();
        String changedText = "(" + currentText + ")";
        textfieldResult.setText(changedText);
    }

    @FXML
    private void addMinus() {
        String currentText = textfieldResult.getText();
        String changedText;
        if (currentText.startsWith("-")) {
            changedText = currentText.substring(1);
        } else {
            changedText = "-" + currentText;
        }
        textfieldResult.setText(changedText);
    }
}