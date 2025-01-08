package org.example.RuletaFutbol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class HelloDemo {
    private JFrame helloDemo;
    private JPanel panelMain, panelInferior, panelCentral, panelVertical;
    private JLabel imagen, titulo;
    private JButton accion;
    private Thread hilo;


    public HelloDemo() {

        montandoMarco();

        montandoPanelSuperior();
        montandoPanelCentral();
        montandoPanelInferior();


    }

    private void montandoPanelSuperior() {

        panelMain.add(panelCentral, BorderLayout.CENTER);
        panelMain.add(panelInferior, BorderLayout.SOUTH);
        helloDemo.add(panelMain);
    }

    private void montandoPanelCentral() {

        panelVertical.setLayout(new BoxLayout(panelVertical, BoxLayout.Y_AXIS));
        panelVertical.add(imagen);
        panelVertical.add(titulo);

        panelCentral.add(panelVertical);

        accion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                hilo = new Thread(new Funcionamiento());
                hilo.start();
            }
        });
            }


    private void montandoPanelInferior() {

        panelInferior.add(accion);
         
    }

    private void montandoMarco() {
        helloDemo = new JFrame("Demo");
        helloDemo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helloDemo.setSize(400, 300);
        helloDemo.setLocationRelativeTo(null);
        helloDemo.setVisible(true);

        panelMain = new JPanel( new BorderLayout());
        panelCentral = new JPanel();
        panelInferior = new JPanel();
        panelVertical = new JPanel();
        imagen = new JLabel("x");
        titulo = new JLabel("xx");
        accion = new JButton("¡Genera jugador de futbol!");

    }

    private class Funcionamiento implements Runnable{

        private final String direccionCarpeta = "C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\SwingDemo\\futbol";
        private File file;
        private File [] files;

        public Funcionamiento() {

            this.file = new File(direccionCarpeta);
            this.files = file.listFiles();
        }

        @Override
        public void run() {

            int numRandom =(int) (Math.random()*25) +10;

            try {
                while (numRandom>=0){
                    Thread.sleep(30);
                    // Generate a random index for the files array
                    int randomIndex = (int) (Math.random() * files.length);
                    ImageIcon icon = new ImageIcon(files[randomIndex].getPath());
                    imagen.setIcon(icon);
                    titulo.setText("Jugador " + ": " + files[randomIndex].getName().substring(0,files[randomIndex].getName().length()-4));
                    numRandom--;
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
       HelloDemo demo = new HelloDemo();
    }
}
