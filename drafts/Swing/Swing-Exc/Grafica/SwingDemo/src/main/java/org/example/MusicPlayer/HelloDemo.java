package org.example.MusicPlayer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HelloDemo {
    private JFrame marco;
    private JPanel principal,central,inferior;
    private JTextField campoTexto;
    private JButton play,selector,stop;
    private File file;
    private Clip clip;
    private AudioInputStream audioInputStream;



    public HelloDemo() {
        file = null;
        montandoMarco();
        montandoPanel();


    }

    public void montandoMarco() {
        marco = new JFrame("Music Player");
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setSize(400, 200);
        marco.setLocationRelativeTo(null);
    }

    public void montandoPanel() {
        principal = new JPanel(new BorderLayout());
        central = new JPanel();
        inferior = new JPanel();
        campoTexto = new JTextField(20);
        selector = new JButton("Abrir");
        play = new JButton("Play");
        stop = new JButton("Stop");
    }

    public void comenzarEscena (){
        montarEscena();
        abrir();

    }



    private void montarEscena() {
        campoTexto.setEditable(false);

        selector.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSelector();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clip != null && clip.isRunning()) {
                    try {
                        clip.stop();
                        clip.close();
                        audioInputStream.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducir();
            }
        });
        //Panel central
        central.add(campoTexto);
        central.add(selector);
        //Panel inferior

        inferior.add(play);
        inferior.add(stop);
        principal.add(central, BorderLayout.CENTER);
        principal.add(inferior, BorderLayout.SOUTH);

    }

    private void reproducir() {
        if (file != null) {
            try {
                 audioInputStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        } else {
            campoTexto.setText("Error");
        }
    }

    private void abrirSelector() {

        JFileChooser selector = new JFileChooser();

        selector.showOpenDialog(selector);
        file = selector.getSelectedFile();
        String nombreArchivo = file.getName();
        
        if (comprobarFormato(nombreArchivo)) {
            campoTexto.setText(nombreArchivo);
            
        } else {
            campoTexto.setText("Error");
            file = null;
        }

    }

    private boolean comprobarFormato(String nombreArchivo) {
        String formato = nombreArchivo.substring(nombreArchivo.length()-4);
        return formato.equalsIgnoreCase(".wav");

    }

    private void abrir() {
        marco.add(principal);
        marco.setVisible(true);
    }

    public static void main(String[] args) {

        HelloDemo helloDemo = new HelloDemo();
        helloDemo.comenzarEscena();
    }
}
