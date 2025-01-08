package org.example.MemoryGame;

import lombok.Getter;
import lombok.Setter;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter@Setter
public class CuadradoMemory extends JButton {
    private Color[] colores = {Color.RED, Color.RED, Color.BLUE, Color.BLUE, Color.GREEN, Color.GREEN, Color.MAGENTA, Color.MAGENTA, Color.CYAN, Color.CYAN, Color.ORANGE, Color.ORANGE, Color.PINK, Color.PINK, Color.YELLOW, Color.YELLOW};
    private int numBoton;
    private static int numStatic = 0;
    private static CuadradoMemory boton1, boton2,aux1,aux2;
    private static int clics = 0, parejas = 0;


    public CuadradoMemory() {

        super();

        numBoton = numStatic;
        numStatic++;
        //

        this.setOpaque(true);
        this.setBorderPainted(true);
        this.setEnabled(true);
        this.addActionListener(e -> {
            compruebaPareja();
        });
    }

    private void compruebaPareja() {
        setBackground(this.colores[numBoton]);
        clics++;

        if(boton1 == null){
            boton1 = this;
            this.setEnabled(false);
        }else{
            boton2 = this;
            boton2.setEnabled(false);
             aux1 = boton1;
            aux2 = boton2;
            boton1 = null;
            boton2 = null;


            Timer temporizador = new Timer(500, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(aux1.getBackground() != aux2.getBackground()){
                        aux1.setBackground(Color.WHITE);
                        aux1.setEnabled(true);
                        aux2.setBackground(Color.WHITE);
                        aux2.setEnabled(true);
                    }else{
                        parejas++;
                        if(parejas == 8){
                            ganar();
                        }
                    }

                }

                private void ganar() {
                    JOptionPane.showMessageDialog(null, "Los has conseguido\n    Intentos: " + clics + "\n");
                    System.exit(0);
                }
            });

            temporizador.setRepeats(false);
            temporizador.start();
        }
    }

    public static List<CuadradoMemory> generaCuadrados() {
        List<CuadradoMemory> cuadrados = new ArrayList<>();

        for (int i = 0; i < 16; i++) {
            cuadrados.add(new CuadradoMemory());
        }

        Collections.shuffle(cuadrados);
        return cuadrados;
    }


    }




