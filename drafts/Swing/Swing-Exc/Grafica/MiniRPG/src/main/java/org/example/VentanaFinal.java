package org.example;

import javax.swing.*;
import java.awt.*;

public class VentanaFinal {
    private JTextArea areaTexto;
    private JLabel imagen;
    private JDialog marco;
    private ImageIcon rutaImagen;
    private JPanel panelPrincipal;
    private JButton botonSalir;
    public static final int VICTORIA = 1;
    public static final int DERROTA = 2;
private int condicion;
private Personaje personaje;

    public VentanaFinal(int condicion, Personaje personaje) {
        marco = new JDialog();
        String ruta = null;
        panelPrincipal = new JPanel(new BorderLayout());
        areaTexto = new JTextArea();
        imagen = new JLabel();
        botonSalir = new JButton("Finalizar");
        this.personaje = personaje;
        this.condicion = condicion;
        if (condicion == VICTORIA) {
            ruta = "C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\victoria.jpg";
            ImageIcon originalIcon = new ImageIcon(ruta);
            // Redimensionar la imagen (por ejemplo, a 100x100 píxeles)
            Image scaledImage = originalIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            rutaImagen= new ImageIcon(scaledImage);
            imagen = new JLabel(rutaImagen);
            areaTexto.setText("Felicidades, has ganado la partida");
        } else if (condicion == DERROTA) {
            ruta = "C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\defeat.png";
            ImageIcon originalIcon = new ImageIcon(ruta);
            // Redimensionar la imagen (por ejemplo, a 100x100 píxeles)
            Image scaledImage = originalIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            rutaImagen = new ImageIcon(scaledImage);
            areaTexto.setText("Has perdido la partida");
        }

        imagen = new JLabel(rutaImagen);

    }
    
    public void abrir(){
        prepararMensaje();
        montarEscena();
        marco.setVisible(true);
    }

    private void prepararMensaje() {
        StringBuilder mensaje = new StringBuilder();
        if (condicion == VICTORIA) {
           mensaje.append("Felicidades, has ganado la partida");
           mensaje.append("Nivel: ").append(personaje.getNivel());
           mensaje.append("Experiencia: ").append(personaje.getExp());
           mensaje.append("Oro: ").append(personaje.getNivel());
           mensaje.append("Vida: ").append(personaje.getVida());
           mensaje.append("Oro: ").append(personaje.getOro());
           mensaje.append("Experiencia: ").append(personaje.getExp());
        } else if (condicion == DERROTA) {
            mensaje.append("Has perdido la partida");
            mensaje.append("Nivel: ").append(personaje.getNivel());
            mensaje.append("Vida: ").append(personaje.getVida());
            mensaje.append("Oro: ").append(personaje.getOro());
            mensaje.append("Experiencia: ").append(personaje.getExp());
        }

        areaTexto.setText(mensaje.toString());
    }

    private void montarEscena() {
        panelPrincipal.add(imagen, BorderLayout.NORTH);
        panelPrincipal.add(areaTexto, BorderLayout.CENTER);
        botonSalir.addActionListener(e -> {
            marco.setVisible(false);
            marco.dispose();
        });
        panelPrincipal.add(botonSalir, BorderLayout.SOUTH);
        marco.add(panelPrincipal);
        marco.setTitle("MiniRPG");
        marco.setSize(800, 600);
        marco.setLocationRelativeTo(null);
        marco.setModal(true);
        marco.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}