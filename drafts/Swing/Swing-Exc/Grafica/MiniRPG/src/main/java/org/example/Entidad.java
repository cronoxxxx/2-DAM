package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
@Getter@Setter
public abstract class Entidad implements IAtacable {

    protected String nombre;
    protected int vida, ataque, defensa;
    protected double vidaMax; //aumento de vida cuando avanza el personaje de niveles
    protected boolean vivo;
    protected JProgressBar barraVida;

    public Entidad( String nombre, int ataque, int defensa, double vidaMax) {
        this.nombre=nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vidaMax = vidaMax;
        vida = (int) vidaMax;
        vivo = true;
        barraVida = new JProgressBar(0, (int) vidaMax);

        barraVida.setPreferredSize(new Dimension(200, 20));
        establecerBarraVida((int) vida);
    }

    public void establecerBarraVida(int vidaActual) {
        barraVida.setValue(vidaActual);
        barraVida.setStringPainted(true);
        barraVida.setForeground(Color.GREEN);
        barraVida.setBackground(Color.BLACK);
        barraVida.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        barraVida.setString(vidaActual + "/" + (int) vidaMax);
    }

    @Override
    public void atacar(IAtacable enemigo) {
        enemigo.recibirHerida(ataque); //el enemigo recibe el ataque de el personaje principal del ataque

    }

    @Override
    public void recibirHerida(int ataque) {
        if (isVivo()) {
            //int cantidad = (int) (herida * (1 - (defensa / 100.0))); //
            int cantidadTotal = ataque - defensa; //hace daño si la defensa es menor que el ataque, sino solo 1
            if (cantidadTotal <= 0) {
                cantidadTotal = 1;
            }
            vida -= cantidadTotal;
            if (vida <= 0) {
                vida = 0;
                vivo = false;
            }

        }

    }



}
