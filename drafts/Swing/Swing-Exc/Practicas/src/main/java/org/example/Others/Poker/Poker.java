package org.example.Others.Poker;

import lombok.Getter;
import lombok.Setter;

//Sacar carta de poker al azar
@Getter
@Setter
public class Poker {
    private String palo,carta;

    public String getPalo(){
        int num = (int) (Math.random()*4)+1;
        switch(num){
            case 1 -> {
                return "Picas";
            }
            case 2 -> {
                return "Diamantes";
            }
            default -> {
                return "Treboles";
            }
        }
    }
    public String getCarta(){
        int num = (int) (Math.random()*13)+1;
        switch(num){
            case 1 -> {
                return "As";
            }
            case 11 -> {
                return "J";
            }
            case 12 -> {
                return "Q";
            }
            case 13 -> {
                return "K";
            }
            default -> {
                return Integer.toString(num);
            }
        }
    }



}
