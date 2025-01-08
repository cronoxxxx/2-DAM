package org.example.TicTacToe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Casilla extends JButton {
    private static boolean turnoJugadorUno = true;

    public Casilla() {
        super();
        this.setFont(new Font("Arial", Font.BOLD, 40));
        this.setBackground(Color.WHITE);
        this.setForeground(Color.BLACK);
        setOpaque(true);
    }


        public static void logicaJuego(Casilla[][] casillas, Casilla source) {
          pintarCasilla(source);
          if (logicaBatallaGanar(casillas)) {
              for (int i = 0; i <  casillas.length; i++) {
                  for (int j = 0; j < casillas[i].length ; j++) {
                      casillas[i][j].setEnabled(false);
                      break;
                  }
              }
          }
    }

    private static void pintarCasilla(Casilla source) {
        if (turnoJugadorUno) {
            source.setText("X");
            turnoJugadorUno = false;
            source.setEnabled(false);

        } else {
            source.setText("O");
            turnoJugadorUno = true;
            source.setEnabled(false);
        }


    }

    private static boolean logicaBatallaGanar(Casilla[][] casillas) {
        // Comprobar filas y columnas
        for (int i = 0; i < 3; i++) {
            if (comprobarLinea(casillas[i][0], casillas[i][1], casillas[i][2]) ||
                    comprobarLinea(casillas[0][i], casillas[1][i], casillas[2][i])) {
                return true;
            }
        }

        // Comprobar diagonales
        if (comprobarLinea(casillas[0][0], casillas[1][1], casillas[2][2]) ||
                comprobarLinea(casillas[0][2], casillas[1][1], casillas[2][0])) {
            return true;
        }

        return false;
    }

    private static boolean comprobarLinea(Casilla c1, Casilla c2, Casilla c3) {
        if (!c1.getText().isEmpty() && c1.getText().equals(c2.getText()) && c1.getText().equals(c3.getText())) {
            String ganador = c1.getText().equals("X") ? "Jugador 1" : "Jugador 2";
            JOptionPane.showMessageDialog(null, "Gana el " + ganador);
            return true;
        }
        return false;
    }
}

