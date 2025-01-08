package com.example.pixelart;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PixelArtScene extends Scene {
    private VBox contenedor, filas;
    private HBox[] cuadradosEnFila;
    private HBox[] colores;

    public PixelArtScene(Parent contenedor, double ancho, double alto) {
        super(contenedor, ancho, alto);

        this.contenedor = (VBox) contenedor;
        this.filas = new VBox(3);
        this.cuadradosEnFila = new HBox[20];
        this.colores = new HBox[2];
        ((VBox) contenedor).setSpacing(20);

        montar();
    }

    private void montar() {
        contenedor.setAlignment(Pos.CENTER);
        for (int i = 0; i < cuadradosEnFila.length; i++) {
            cuadradosEnFila[i] = new HBox(3);
            cuadradosEnFila[i].setAlignment(Pos.TOP_CENTER);
            for (int j = 0; j < cuadradosEnFila.length; j++) {
                cuadradosEnFila[i].getChildren().add(new SquareColor());
            }
            this.filas.getChildren().add(cuadradosEnFila[i]);
        }

        prepararColores();

        this.contenedor.getChildren().addAll(this.filas, this.colores[0], this.colores[1]);


    }

    private void prepararColores() {
        SquareColor blanco = new SquareColor("-fx-background-color: white;");
        SquareColor negro = new SquareColor("-fx-background-color: black;");
        SquareColor rojo = new SquareColor("-fx-background-color: red;");
        SquareColor verde = new SquareColor("-fx-background-color: green;");
        SquareColor azul = new SquareColor("-fx-background-color: blue;");
        SquareColor amarillo = new SquareColor("-fx-background-color: yellow;");
        SquareColor cyan = new SquareColor("-fx-background-color: cyan;");
        SquareColor magenta = new SquareColor("-fx-background-color: magenta;");
        SquareColor naranja = new SquareColor("-fx-background-color: orange;");
        SquareColor gris = new SquareColor("-fx-background-color: gray;");
        SquareColor marron = new SquareColor("-fx-background-color: brown;");
        SquareColor rosa = new SquareColor("-fx-background-color: pink;");
        SquareColor violeta = new SquareColor("-fx-background-color: violet;");
        SquareColor dorado = new SquareColor("-fx-background-color: gold;");
        SquareColor plateado = new SquareColor("-fx-background-color: silver;");
        SquareColor aqua = new SquareColor("-fx-background-color: aquamarine;");
        SquareColor beige = new SquareColor("-fx-background-color: beige;");
        SquareColor lavanda = new SquareColor("-fx-background-color: lavender;");
        SquareColor oliva = new SquareColor("-fx-background-color: olive;");
        SquareColor coral = new SquareColor("-fx-background-color: coral;");
        SquareColor turquesa = new SquareColor("-fx-background-color: turquoise;");

        for (int i = 0; i < colores.length; i++) {
            colores[i]= new HBox(15);
            colores[i].setAlignment(Pos.CENTER);
        }
        colores[0].getChildren().addAll(blanco, negro, rojo, verde, azul, amarillo, cyan, magenta, naranja, gris);
        colores[1].getChildren().addAll(marron, rosa, violeta, dorado, plateado, aqua, beige, lavanda, oliva, coral, turquesa);




    }
}
