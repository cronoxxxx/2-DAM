package org.example.Others.OnePiece;

public class OnePiece {
    private String[][] mapa;
    private int x, y, contador;

    public OnePiece() {
        this.mapa = new String[11][11];

        this.x = (int) (Math.random() * 9 + 1);
        this.y = (int) (Math.random() * 9 + 1);
        this.contador = 0;
    }

    public void printMap() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if (i == 0 && j != 0) {
                    mapa[i][j] = String.valueOf(j);
                } else if (j == 0 && i != 0) {
                    mapa[i][j] = String.valueOf(i);
                } else if (i != 0) {
                    mapa[i][j] = "?";
                } else {
                    mapa[i][j] = " ";
                }
            }
        }
    }

    public void dibujaMapa() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void prueba() {
        printMap();
        dibujaMapa();
    }

    public boolean exposeResults(int x, int y) {
        // Ensure x and y are within the bounds of the map
        if (x <= 0 || y <= 0 || x >= mapa.length || y >= mapa[0].length) {
            System.out.println("Coordenadas fuera de los límites. Intenta de nuevo.");
            return false; // Return false if coordinates are out of bounds
        }

        contador++;

        // Check if the guessed coordinates match the treasure's coordinates
        if (this.x == x && this.y == y) {
            mapa[x][y] = "X"; // Place 'X' at the correct position
            dibujaMapa(); // Draw the map before returning true
            System.out.println("¡Has encontrado el tesoro!");
            return true; // Return true if the treasure is found
        } else {
            // Provide directional hints
            if (this.x != x) { // If y coordinates are different
                if (this.x < x) { // If the treasure is above
                    mapa[x][y] = "↑";
                } else { // If the treasure is below
                    mapa[x][y] = "↓";
                }
            } else { // If x coordinates are different
                if (this.y < y) { // If the treasure is to the left
                    mapa[x][y] = "←";
                } else { // If the treasure is to the right
                    mapa[x][y] = "→";
                }
            }
            dibujaMapa(); // Draw the map after placing the hint
        }

        return false; // Return false if the treasure is not found
    }
}
