package  org.example.MemoryGameImg;

import lombok.Getter;
import lombok.Setter;


import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CuadradoMemory extends JButton {
    private String[] imagenes = {
            "logos/0.png", "logos/0.png",
            "logos/1.png", "logos/1.png",
            "logos/2.png", "logos/2.png",
            "logos/3.png", "logos/3.png",
            "logos/4.png", "logos/4.png",
            "logos/5.png", "logos/5.png",
            "logos/6.png", "logos/6.png",
            "logos/7.png", "logos/7.png"
    };
    private int numBoton;
    private static int numStatic = 0;
    private static CuadradoMemory boton1, boton2, aux1, aux2;
    private static int clics = 0, parejas = 0;
    private PanelJuegoMemory panelJuegoMemory;
    private ImageIcon imagen;

    public CuadradoMemory(PanelJuegoMemory panelJuegoMemory) {
        super();
        this.panelJuegoMemory = panelJuegoMemory;
        numBoton = numStatic;
        numStatic++;

        this.setOpaque(true);
        this.setBorderPainted(true);
        this.setEnabled(true);
        this.addActionListener(e -> {
            compruebaPareja();
        });

        // Cargar la imagen correspondiente
        imagen = new ImageIcon(imagenes[numBoton]);
        // Establecer un icono por defecto (puede ser una imagen de fondo o null)
        this.setIcon(null);
    }

    private void compruebaPareja() {
        setIcon(imagen);
        clics++;

        if (boton1 == null) {
            boton1 = this;
            this.setEnabled(false);
        } else {
            boton2 = this;
            boton2.setEnabled(false);
            aux1 = boton1;
            aux2 = boton2;
            boton1 = null;
            boton2 = null;

            Timer temporizador = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!aux1.getImagenPath().equals(aux2.getImagenPath())) {
                        aux1.setIcon(null);
                        aux1.setEnabled(true);
                        aux2.setIcon(null);
                        aux2.setEnabled(true);
                    } else {
                        parejas++;
                        if (parejas == 8) {
                            ganar();
                        }
                    }
                }

                private void ganar() {
                    JOptionPane.showMessageDialog(null, "Lo has conseguido\n    Intentos: " + clics + "\n");
                    System.exit(0);
                }
            });

            temporizador.setRepeats(false);
            temporizador.start();
        }
    }

    public static List<CuadradoMemory> generaCuadrados(PanelJuegoMemory panelJuegoMemory) {
        List<CuadradoMemory> cuadrados = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            cuadrados.add(new CuadradoMemory(panelJuegoMemory));
        }

        Collections.shuffle(cuadrados);
        return cuadrados;
    }

    public String getImagenPath() {
        return imagenes[numBoton];
    }
}



