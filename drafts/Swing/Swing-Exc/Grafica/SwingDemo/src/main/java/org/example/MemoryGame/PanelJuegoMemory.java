package org.example.MemoryGame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelJuegoMemory extends JPanel {
    private List<CuadradoMemory> cuadrados;

    public PanelJuegoMemory() {
        super();
        this.setLayout(new GridLayout(4, 4));

        cuadrados = CuadradoMemory.generaCuadrados();
        for (CuadradoMemory cuadrado : cuadrados) {
            this.add(cuadrado);
        }

    }
}
