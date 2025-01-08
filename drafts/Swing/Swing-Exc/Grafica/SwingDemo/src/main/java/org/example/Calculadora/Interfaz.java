package org.example.Calculadora;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.List;



public class Interfaz {
    private JFrame ventana;
    private JPanel panelSuperior;
    private JPanel panelInferior;
    private JTextField resultado;
    private List<JButton> botones;

    public Interfaz() {
        botones = new ArrayList<>();
        botones = List.of(
                new JButton("7"),
                new JButton("8"),
                new JButton("9"),
                new JButton("4"),
                new JButton("5"),
                new JButton("6"),
                new JButton("1"),
                new JButton("2"),
                new JButton("3"),
                new JButton("0"),
                new JButton("."),
                new JButton("+"),
                new JButton("-"),
                new JButton("*"),
                new JButton("/"),
                new JButton("="));
        montandoMarco();
    }

    private void montandoparteSuperior() {
        resultado = new JTextField("0",10);
        resultado.setEditable(false); // Hacer que el campo de texto no sea editable
        resultado.setHorizontalAlignment(JTextField.CENTER);
        panelSuperior.add(resultado);
    }

    private void montandoMarco() {
        ventana = new JFrame("Calculadora");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(300, 400);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new BorderLayout());

        panelSuperior = new JPanel();
        panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(4, 4)); // Configurar el diseño del panel inferior

        ventana.add(panelSuperior, BorderLayout.NORTH);
        ventana.add(panelInferior, BorderLayout.CENTER);

        montandoparteSuperior();
        montandoInterfazBotonesCalculadora();

        ventana.setVisible(true);
    }

    private void montandoInterfazBotonesCalculadora() {
        for (JButton boton : botones) {
            panelInferior.add(boton);
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = boton.getText();
                    if (command.equals("=")) {
                        evaluarExpresion();
                    } else {
                        if (resultado.getText().equals("0")) {
                            resultado.setText(command);
                        } else {
                            resultado.setText(resultado.getText() + command);
                        }
                    }
                }
            });
        }
    }

    private void evaluarExpresion() {
        String input = resultado.getText();
        if (!input.isEmpty()) {
            ArrayList<String> expressionParts = new ArrayList<>();
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);

                expressionParts.add(String.valueOf(c));
            }
            System.out.println( expressionParts);

            // Now you can process the expressionParts list to evaluate the expression
            double result = evaluateExpressionParts(expressionParts);
            resultado.setText(String.valueOf(result));
        }
    }

    private double evaluateExpressionParts(ArrayList<String> expressionParts) {
        // Simple implementation assuming no parentheses and left-to-right evaluation
        double result = Double.parseDouble(expressionParts.get(0));
        for (int i = 1; i < expressionParts.size(); i += 2) {
            String operator = expressionParts.get(i);
            double nextNumber = Double.parseDouble(expressionParts.get(i + 1));

            switch (operator) {
                case "+":
                    result += nextNumber;
                    break;
                case "-":
                    result -= nextNumber;
                    break;
                case "*":
                    result *= nextNumber;
                    break;
                case "/":
                    if (nextNumber == 0) {
                        throw new ArithmeticException("Cannot divide by zero");
                    }
                    result /= nextNumber;
                    break;
            }
        }
        return result;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(Interfaz::new);
    }
}