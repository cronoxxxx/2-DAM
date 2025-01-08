package org.example.ATS.C1;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {

    public Ventana() {
        //this.getContentPane().setBackground(Color.CYAN);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(200, 200));
        setSize(500, 500);
        setLocationRelativeTo(null);
        //setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Ventana");
        iniciarComponentes();
    }
    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.CYAN);
        this.getContentPane().add(panel);
    }
}
