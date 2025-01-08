package org.example.MoveMonster;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Ventana extends JFrame {
    private int x, y;
    private Image imagen;

    public Ventana() {
        this.x = 0;
        this.y = 0;
        try {
            imagen = ImageIO.read(new File("./prueba/jacky.png"));
            imagen = imagen.getScaledInstance(250, 350, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setTitle("Moviendo al monstruo");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(new ParedMonstruo());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int newX = x;
                int newY = y;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        newY -= 10;
                        break;
                    case KeyEvent.VK_DOWN:
                        newY += 10;
                        break;
                    case KeyEvent.VK_LEFT:
                        newX -= 10;
                        break;
                    case KeyEvent.VK_RIGHT:
                        newX += 10;
                        break;
                }

                // Comprobar límites antes de actualizar las posiciones
                if (newX >= 0 && newX <= getWidth() - imagen.getWidth(null)) {
                    x = newX;
                }
                if (newY >= 0 && newY <= getHeight() - imagen.getHeight(null)) {
                    y = newY;
                }

                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });


    }

    private class ParedMonstruo extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(imagen, x, y, this);
        }
    }

    public static void main(String[] args) {
        new Ventana();
    }
}
