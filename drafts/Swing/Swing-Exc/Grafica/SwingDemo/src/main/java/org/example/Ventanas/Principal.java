package org.example.Ventanas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    private int x = 0, y = 0;

    private List<JDialog> ventanas;
    private JFrame frame;

    public Principal() {
        frame = new JFrame();
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Ventana Principal");

        JPanel panel = new JPanel(new GridBagLayout());
        JButton btn = new JButton("Crear ventana");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btn, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);


        //lambda
        btn.addActionListener(e -> crearVentana());
        ventanas = new ArrayList<>();
    }


    private void crearVentana() {
        JDialog dialog ;
        if (ventanas.isEmpty()){
            dialog = new JDialog(frame);
        } else {
            dialog = new JDialog(ventanas.getLast(), ventanas.getLast().getTitle());
        }
        dialog.setBounds(x, y, 10, 10);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                x = dialog.getX();
                y = dialog.getY();
            }
        });
        x += 20;
        y += 20;
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
        ventanas.add(dialog);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Principal::new);
    }
}