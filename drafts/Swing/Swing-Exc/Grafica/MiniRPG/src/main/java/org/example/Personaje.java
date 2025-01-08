package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Personaje extends Entidad {
    private int nivel,oro,exp, experienciaNecesaria;
    public Personaje(String nombre, int ataque, int defensa, double vidaMax) {
        super(nombre, ataque, defensa, vidaMax);
        nivel = 1;
        exp = 0;
        oro = 0;
        experienciaNecesaria = 10;
    }

    public void subirNivel() {
        nivel++;
        setAtaque(ataque+2);
        setDefensa(defensa+1);
        setVidaMax((int) (vidaMax * 1.2)); // Aumentar la vida máxima en un 20%
        barraVida.setMaximum((int) vidaMax);
        setVida((int) vidaMax);
        establecerBarraVida((int) vidaMax);
        experienciaNecesaria += experienciaNecesaria+5;
    }

    public void subirExperiencia(int experiencia) {
        exp += experiencia;
        if (exp >= experienciaNecesaria) {
            subirNivel();
        }
    }

}
