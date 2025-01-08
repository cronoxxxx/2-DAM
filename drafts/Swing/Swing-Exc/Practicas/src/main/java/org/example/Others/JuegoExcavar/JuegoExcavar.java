package org.example.Others.JuegoExcavar;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public class JuegoExcavar {
    private int derrumbe, diamante, carbon, oro, diamantes;
    private boolean derrumbado, jugando;

    public JuegoExcavar() {
        this.derrumbe = 1; //fin de juego
        this.diamante = 1;
        this.carbon = 0;
        this.oro = 0;
        this.diamantes = 0;
        this.derrumbado = false;
        this.jugando = true;
    }

    public void jugar() {
        do {
            menuEleccion();
        } while (!derrumbado && jugando);
    }

    public void menuEleccion() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Elige una opcion:");
        System.out.println("1. Seguir jugando");
        System.out.println("2. Salir");
        int eleccion;
        boolean opcionValida = false;

        do {
            try {
                eleccion = entrada.nextInt();
                switch (eleccion) {
                    case 1 -> {
                        opcionValida = true;
                        cavar();
                    }
                    case 2 -> {
                        System.out.println("vuelva pronto");
                        opcionValida = true;
                        jugando = false;
                    }
                    default -> {
                        System.out.println("Opcion no valida");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace(System.out);
                entrada.nextLine();
            }
        } while (!opcionValida);


    }

    private void cavar() {
        int d = (int) (Math.random() * 100 + 1);
        if (d <= derrumbe) {
            derrumbado = true;
            System.out.println("Has sido derrumbado");
        } else {
            int material = (int) (Math.random() * 100 + 1);
            if (material <= diamante) {
                System.out.println("Diamante");
                diamantes++;
            } else if (material <= (20 + diamante)) {
                System.out.println("oro");
                oro++;
            } else {
                System.out.println("carbon");
                carbon++;
            }

            derrumbe++;
            diamante += 2; //reglas random
        }


    }

}
