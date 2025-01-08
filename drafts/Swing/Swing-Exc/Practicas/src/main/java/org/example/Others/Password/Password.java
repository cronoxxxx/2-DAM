package org.example.Others.Password;

public class Password {
    private static String generadorContrasenas (int longitud, boolean esDiferentesLetras){
         Character letra=null;
         StringBuilder contrasena= new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            if(esDiferentesLetras) {
                letra = (char) (Math.random() * 26 + 97);
            } else {
                //solo letras, numeros y otros caracteres especiales juntos
                letra = (char) (Math.random() * 67 + 48);
            }

            contrasena.append(letra);

        }

        return contrasena.toString();


    }

    public static void main(String[] args) {
        System.out.println(        generadorContrasenas(10, false)
        );
    }
}
