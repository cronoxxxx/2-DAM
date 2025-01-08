package com.example.primerproyecto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        File archivo = new File("C:\\Users\\Adrian\\Desktop\\prueba\\coco.wav");

            Media n = new Media(archivo.toURI().toString());
            MediaPlayer mp = new MediaPlayer(n);
            mp.play();


    }
}