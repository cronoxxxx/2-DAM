package org.example.Others.Combate;

public class Tester {
    public static void main(String[] args) {
        Heroe heroe = new Heroe("Heroe", 100, 10);
        Villano villano = new Villano("Villano", 100, 10);
        do {
             heroe.atacar(villano);
             if (villano.estaVivo()) {
                 villano.atacar(heroe);
             }
        } while (heroe.estaVivo() && villano.estaVivo());

        if (heroe.estaVivo() && !villano.estaVivo()) {
            System.out.println(heroe.nombre + " ha ganado la batalla");
        } else {
            System.out.println(villano.nombre + " ha ganado la batalla");
        }


    }
}

