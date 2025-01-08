package org.example.ClickCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickCounter {
    private HelloDemo helloDemo;
    private PanelPer mainPanel;
    private PanelPer panelInferior;
    private JButton boton;
    private JLabel etiquetaClics;
    private JLabel etiquetaSegs;

    private static int CLICS = 0;
    private static int SEGS = (int) (Math.random() * 50) + 10;

    private Thread hilo;


    public ClickCounter(int x, int y, String title, boolean esPrincipal) {
        helloDemo = new HelloDemo(x, y, title, esPrincipal);
        mainPanel = new PanelPer(PanelPer.BORDERLAYOUT);
        panelInferior = new PanelPer(PanelPer.FLOWLAYOUT);
        boton = new JButton("Empezar");
        etiquetaClics = new JLabel("0", SwingConstants.CENTER);
        etiquetaSegs = new JLabel(Integer.toString(SEGS), SwingConstants.CENTER);
        hilo = new Thread(new Logica());
        hilo.start();
        montandoPanelSuperior();
        montandoPanelInferior();
        montandoMarco();
        boton.addActionListener(comienzo);
    }

    public ClickCounter() {
        this(300, 300, "Click Counter", true);
    }

    private void montandoPanelSuperior() {
        etiquetaClics.setFont(new Font("Arial", Font.BOLD, 40));
        etiquetaClics.setForeground(Color.BLUE);
        mainPanel.add(etiquetaClics, BorderLayout.NORTH);
    }

    private void montandoPanelInferior() {

        panelInferior.add(etiquetaSegs);
        panelInferior.add(boton);
        mainPanel.add(panelInferior, BorderLayout.CENTER);
    }

    private void montandoMarco() {
        helloDemo.setLayout(new GridBagLayout()); // Center the main panel
        helloDemo.add(mainPanel);
        helloDemo.setVisible(true);
    }

    ActionListener comienzo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            boton.setText("Comenzar");
            boton.removeActionListener(comienzo);
            boton.addActionListener(actionListener);
        }
    };


    //Creando action listeners
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            CLICS++;
            etiquetaClics.setText(Integer.toString(CLICS));
        }
    };

    private class Logica implements Runnable {


        @Override
        public void run() {
            while (SEGS>0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
                SEGS--;
                etiquetaSegs.setText(Integer.toString(SEGS));


            }

            finalizarJuego();
        }
    }

    private void finalizarJuego() {
        boton.setEnabled(false);
        hilo.interrupt();
        ventanaPuntos();
        boton.removeActionListener(actionListener);
        boton.addActionListener(comienzo);
        boton.setText("Comenzar");
        etiquetaClics.setText("0");
    }

    private void ventanaPuntos() {
        JDialog ventanaPuntos = new JDialog(helloDemo, "Puntos");
        ventanaPuntos.setLayout(new FlowLayout());
        ventanaPuntos.setLocationRelativeTo(null);
        JPanel panelPuntos = new JPanel();

        // Construcción del texto con HTML
        StringBuilder sb = new StringBuilder("Haz clicado " + CLICS + " veces. ");
        if (CLICS < 50) {
            sb.append("Creo que puedes mejorarlo");
        } else if (CLICS < 100) {
            sb.append("Los resultados son muy buenos");
        } else {
            sb.append("Felicidades, eres un crack");
        }


        JLabel etiquetaClics = new JLabel(sb.toString());

        // Configuración del color del texto
        if (CLICS < 50) {
            etiquetaClics.setForeground(Color.RED);
        } else if (CLICS < 100) {
            etiquetaClics.setForeground(Color.GREEN);
        } else {
            etiquetaClics.setForeground(Color.BLUE);
        }

        panelPuntos.add(etiquetaClics);

        ventanaPuntos.add(panelPuntos);
        ventanaPuntos.setSize(200, 200);
        ventanaPuntos.setVisible(true);
    }


}
