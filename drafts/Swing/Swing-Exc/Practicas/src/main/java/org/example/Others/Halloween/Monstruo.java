package org.example.Others.Halloween;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public abstract class Monstruo {
    protected String nombre;
    protected double costeInvocacion;
    protected boolean estaVivo;

    public Monstruo(String nombre, double costeInvocacion) {
        this.nombre = nombre;
        this.costeInvocacion = costeInvocacion;
        this.estaVivo = true;
    }
}
