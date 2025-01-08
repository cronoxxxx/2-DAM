package org.example.Others.Banco;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter@Setter
public class Banco {

    private List<Cuenta> cuenta;

    public Banco() {
        this.cuenta = new ArrayList<>();
    }
    public boolean transferencia (Cuenta cuentaOrigen, Cuenta cuentaDestino, double cantidad){
        if(cuentaOrigen.getSaldo()>= cantidad){
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo()-cantidad);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo()+cantidad);
            return true;
        }else{
            return false;
        }
    }






}
