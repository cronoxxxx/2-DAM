package org.example.MemoryGame;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        super("Memory Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.add(new PanelJuegoMemory());
    }

    public static void main(String[] args) {
        new VentanaPrincipal();
    }
}
