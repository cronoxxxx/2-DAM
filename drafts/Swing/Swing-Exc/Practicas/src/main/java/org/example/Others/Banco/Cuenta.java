package org.example.Others.Banco;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter@Setter
public class Cuenta {
    private String titular;
    private double saldo;
    private int anyo;

    public Cuenta(String titular, double saldo, int anyo) {
        this.titular = titular;
        this.saldo = saldo;
        this.anyo = anyo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Double.compare(saldo, cuenta.saldo) == 0 && anyo == cuenta.anyo && Objects.equals(titular, cuenta.titular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titular, saldo, anyo);
    }

    @Override
    public String toString() {
        return String.format("Cuenta [titular=%s, saldo=%s, anyo=%s]", titular, saldo, anyo);
    }
}
