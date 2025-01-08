package org.example.Others.Combate;

public class Villano extends PersonajeBase {
    public Villano(String nombre, int vida, int ataque) {
        super(nombre, vida, ataque);
    }

    public void atacar(Personaje personaje) {
        int danio;
        if (getAleatorio().nextInt(10) != 0) {
            danio = getAleatorio().nextInt(getAtaque());
            personaje.recibirDanio(danio);
            System.out.println(nombre+ " ataca a " + personaje.getNombre() + " y le hace " + danio + " de danio");
        } else {
            setVida(getVida()+25);
            System.out.println(getNombre() + " ha regenerado 25 puntos de vida");
            System.out.println(getNombre() + " ahora tiene " + getVida() + " puntos de vida");
        }



    }
}
