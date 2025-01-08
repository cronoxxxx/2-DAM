package org.example.Others.Halloween;

public class Esqueleto extends Monstruo{

    private int cantidadHuesos;
    public Esqueleto(String nombre, double costeInvocacion) {
        super(nombre, costeInvocacion);
        this.cantidadHuesos = 206;
    }

    public void lanzarHuesos(int c){
        if (isEstaVivo()){
            if (c > cantidadHuesos){
                c = cantidadHuesos;
            }

            if (cantidadHuesos <= 0){
                setEstaVivo(false);
                System.out.println("Has muerto");
            } else {
                cantidadHuesos-=c;
                System.out.println("Lanzaste "+c+" huesos");
                System.out.println("Le querestan "+cantidadHuesos+" huesos");
            }
        }


    }
}
