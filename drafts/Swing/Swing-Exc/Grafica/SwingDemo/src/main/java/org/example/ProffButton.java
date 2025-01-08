package org.example;

import javax.swing.*;

public class ProffButton {
    public static void main(String[] args) {
       ImageIcon icon = new ImageIcon("C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\SwingDemo\\futbol\\Messi.jpg");
        JButton button = new JButton(icon);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(button);
    }
}
