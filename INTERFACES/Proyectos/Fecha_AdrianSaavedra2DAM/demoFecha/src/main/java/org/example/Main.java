package org.example;

import org.example.exceptions.FechaException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Demo Fecha");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            JLabel labelinicio = new JLabel("Fecha de inicio");
            JLabel labelfinal = new JLabel("Fecha de fin");
            ComponenteFecha startDateComponent = new ComponenteFecha();
            ComponenteFecha endDateComponent = new ComponenteFecha();
            JButton calcularDiferenciaButton = getjButton(startDateComponent, endDateComponent, frame);
            JPanel panel = new JPanel(new GridLayout(5, 1));
            panel.add(labelinicio);
            panel.add(startDateComponent);
            panel.add(labelfinal);
            panel.add(endDateComponent);
            JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panel2.add(calcularDiferenciaButton);
            panel.add(panel2);
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JButton getjButton(ComponenteFecha startDateComponent, ComponenteFecha endDateComponent, JFrame frame) {
        JButton calcularDiferenciaButton = new JButton("Calcular Diferencia");
        calcularDiferenciaButton.addActionListener(e -> {
            try {
                LocalDate fechaInicio = startDateComponent.getDate();
                LocalDate fechaFin = endDateComponent.getDate();
                long diferencia = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
                JOptionPane.showMessageDialog(frame, "La diferencia es de " + diferencia + " días.");
            } catch (FechaException ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return calcularDiferenciaButton;
    }
}
