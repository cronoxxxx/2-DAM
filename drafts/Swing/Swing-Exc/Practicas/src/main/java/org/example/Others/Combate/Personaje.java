package org.example.Others.Combate;

public interface Personaje {

    void atacar(Personaje personaje);
    void recibirDanio(int danio);
    boolean estaVivo();
    String getNombre();

}
