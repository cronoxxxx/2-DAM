package org.example.Others.Combate;

public class Heroe extends PersonajeBase {
    public Heroe(String nombre, int vida, int ataque) {
        super(nombre, vida, ataque);
    }

    public void atacar(Personaje personaje) {
        int danio;
        if (getAleatorio().nextInt(10) != 0) {
            danio = getAleatorio().nextInt(getAtaque());
        } else {
            danio = getAleatorio().nextInt(getAtaque())*3;
        }

        System.out.println(nombre + " ataca a " + personaje.getNombre() + " y le hace " + danio + " de danio");
        personaje.recibirDanio(danio);
    }
}
