package org.example.Reviews;

import java.util.Scanner;

//Extraer numeros pares
public class E1 {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Ingrese un numero");
        long number = entrada.nextLong();
        String result = String.valueOf(number);
        StringBuilder sb = new StringBuilder(result).reverse();
        System.out.println(sb);


    }

}
