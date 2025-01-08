package org.example.Others.Serpiente;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter@Setter
public class Serpiente {
    private int posActual,anchoMaximo;
    private boolean primeraVez;

    public Serpiente() {
        this.primeraVez=true;
        this.posActual=15;
        this.anchoMaximo=30;
    }



    public void print (){
        while (true){ //infinito
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < anchoMaximo; i++) {
                if(i==posActual){
                    if(primeraVez){
                        System.out.print("@");
                        primeraVez=false;
                    } else {
                        System.out.print("|^|");
                    }
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
            posActual+=(int) (Math.random()*3)-1; //posicion de derecha a izquierda
            if(posActual==-1){
                posActual=0;
            } else if (posActual==anchoMaximo+1){
                posActual=anchoMaximo;
            }
        }
    }




}
