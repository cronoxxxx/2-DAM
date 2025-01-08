package org.example.Others.Combate;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;
@Setter@Getter
public abstract class PersonajeBase implements Personaje {
    protected String nombre;
    protected int vida,ataque;
    protected Random aleatorio;

    public PersonajeBase(String nombre, int vida, int ataque) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
        this.aleatorio = new Random();
    }

    @Override
    public void atacar(Personaje personaje) {

    }

    @Override
    public void recibirDanio(int danio) {
        vida-=danio;
        if(vida<=0){
            vida=0;
        }
        System.out.println(nombre+"Le quedan "+vida+" puntos de vida");
    }

    @Override
    public boolean estaVivo() {
        return vida > 0;
    }

    @Override
    public String getNombre() {
        return "";
    }
}
