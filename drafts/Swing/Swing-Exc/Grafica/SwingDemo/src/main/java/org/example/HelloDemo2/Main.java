package org.example.HelloDemo2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main {
    public static void main(String[] args) {
        HelloDemo2 frame = new HelloDemo2(400, 300, "Hello Demo", true);
        PanelPer panel = new PanelPer(PanelPer.BORDERLAYOUT);
        JButton btn = new JButton("Click me");

        //Agregando listener

        //btn.addActionListener(e -> {
            //String name = JOptionPane.showInputDialog("Enter your name");
            //JOptionPane.showMessageDialog(null, "Hello " + name);
        //});

        btn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed");
                // You can leave this method empty if you don't need to do anything when the mouse is pressed
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse entered");
                // You can leave this method empty if you don't need to do anything when the mouse enters the component
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse exited");
            }
        });

        btn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("so");
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Que");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(e.getKeyChar());
            }
        });
        panel.add(btn, BorderLayout.SOUTH);
        frame.add(panel);
        frame.setVisible(true);
    }
}
