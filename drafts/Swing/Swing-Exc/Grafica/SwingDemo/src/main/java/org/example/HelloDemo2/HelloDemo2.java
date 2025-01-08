package org.example.HelloDemo2;

import javax.swing.*;

public class HelloDemo2 extends JFrame {
    public HelloDemo2(int x, int y, String titulo, boolean esPrincipal) {
        super(titulo);
        setSize(x, y);
        this.setLocationRelativeTo(null);
        if(esPrincipal) {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
