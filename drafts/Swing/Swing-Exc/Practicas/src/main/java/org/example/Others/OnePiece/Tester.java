package org.example.Others.OnePiece;

import java.util.Scanner;

public class Tester {
    public static void main(String[] args) {
        OnePiece onePiece = new OnePiece();
        onePiece.prueba();
        int x,y;
        do {
            System.out.println("Ingresa la coordenada x: ");
            x = new Scanner(System.in).nextInt();
            System.out.println("Ingresa la coordenada y: ");
            y = new Scanner(System.in).nextInt();
        }while (!onePiece.exposeResults(x,y));
    }
}
