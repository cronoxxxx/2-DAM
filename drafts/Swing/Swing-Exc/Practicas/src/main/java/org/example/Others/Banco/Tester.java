package org.example.Others.Banco;

import java.util.List;

public class Tester {
    public static void main(String[] args) {

        Banco banco = new Banco();
        List<Cuenta> cuentas = banco.getCuenta();
        Cuenta cuenta = new Cuenta("Adrian", 1000, 2022);
        Cuenta cuenta2 = new Cuenta ("Miguel", 2000, 2021);
        cuentas.add(cuenta);
        cuentas.add(cuenta2);
        if(banco.transferencia(cuenta, cuenta2, 500)){
            System.out.println("Se ha transferido");
            for (Cuenta cuentaPrint : cuentas) {
                System.out.println(cuentaPrint.toString());
            }
        }else{
            System.out.println("No se ha transferido");
        }

    }
}
