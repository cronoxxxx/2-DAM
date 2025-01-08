package org.example.Reviews;

import java.util.Scanner;

//Es capicuo numero
public class E2 {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Ingresa un numero");
        long number = entrada.nextLong();
        String result = String.valueOf(number);
        StringBuilder sb = new StringBuilder(result).reverse();
        System.out.println(sb);
        if (result.equals(sb.toString())) {
            System.out.println("Es capicua");
        } else {
            System.out.println("No es capicua");
        }
    }
}
