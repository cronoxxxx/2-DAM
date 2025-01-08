package org.example.TicTacToe;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal {
    private JFrame frame;
    private JPanel panel;
    private Casilla[][] casillas;

    public VentanaPrincipal() {
        frame = new JFrame("Tic Tac Toe");
        panel = new JPanel(new GridLayout(3, 3));

        casillas = new Casilla[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                casillas[i][j] = new Casilla();
                panel.add(casillas[i][j]);
                casillas[i][j].addActionListener(e -> {
                    Casilla.logicaJuego(casillas, (Casilla) e.getSource());
                });
            }
        }
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}
