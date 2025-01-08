package org.example.Others.Poker;

public class Tester {
    public static void main(String[] args) {
        String palo = new Poker().getPalo();
        String carta = new Poker().getCarta();
        System.out.println("Palo: "+palo);
        System.out.println("Carta: "+carta);
    }
}
