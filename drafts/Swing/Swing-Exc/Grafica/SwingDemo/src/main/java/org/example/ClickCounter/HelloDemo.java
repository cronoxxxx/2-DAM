package org.example.ClickCounter;

import javax.swing.*;

public class HelloDemo extends JFrame {
    public HelloDemo(int x, int y, String title, boolean esPrincipal) {
        super(title);
        setSize(x, y);
        this.setLocationRelativeTo(null);
        if(esPrincipal) {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }




}
