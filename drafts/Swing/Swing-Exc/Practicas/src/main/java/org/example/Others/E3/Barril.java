package org.example.Others.E3;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Barril {
    private int capacidad;
    private final int ANCHO = 10;
    private int agua;

    public Barril(int capacidad) {
        this.capacidad = capacidad;
        this.agua = (int) (Math.random() * capacidad + 1);
    }
    public void print (){
        for (int i = capacidad; i >=0 ; i--) {
            for (int j = ANCHO; j >0 ; j--) {
                if (i==0){
                    System.out.print("*");
                } else if (j==ANCHO || j==1){
                    System.out.print("*");
                } else if (i<=agua){
                    System.out.print("=");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
            
        }
    }
    }






