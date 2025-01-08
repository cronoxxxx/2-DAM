package org.example.Others.Halloween;

public class Invocador {
    private double energia;

    public Invocador(double energia) {
        this.energia = energia;
    }

    public Monstruo invocar(Monstruo monstruo) {
        if (energia >= monstruo.getCosteInvocacion()) {
            monstruo.estaVivo = true;
            energia -= monstruo.getCosteInvocacion();
            System.out.println("Has invocado a " + monstruo.getNombre());
            return monstruo;
        } else {
            System.out.println("No tienes suficiente energia");
            return null;
        }
    }
}
