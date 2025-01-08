package org.example.Others.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Temporizador {
    static int segundos;
    public static void main(String[] args) {
        // Crear un temporizador
        System.out.println("Cuantos segundos quieres poner?");
        segundos = new Scanner(System.in).nextInt();
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Segundos que faltan para terminar: "+segundos);
                segundos--;
                if(segundos==0){
                    Toolkit.getDefaultToolkit().beep();
                }

            }
        });

        while (segundos>=0){
            timer.start();
        }
        timer.stop();
    }
}
