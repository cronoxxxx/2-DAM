package com.example.pixelart;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class SquareColor extends Button {
    private double lado;
    private String colorCSS;

    public SquareColor() {
        this.lado = 30;
        this.setShape(new Rectangle(lado, lado));
        this.setMinSize(lado, lado);
        this.setMaxSize(lado, lado);

    }

    public SquareColor(String colorCSS) {
        this.lado = 30;
        this.setShape(new Rectangle(lado, lado));
        this.setMinSize(lado, lado);
        this.setMaxSize(lado, lado);
        this.setStyle(colorCSS + "-fx-border-width:1px;-fx-border-color: black");
        this.colorCSS = colorCSS;
    }
}
