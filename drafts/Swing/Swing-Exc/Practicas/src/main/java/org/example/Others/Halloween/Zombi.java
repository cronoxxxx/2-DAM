package org.example.Others.Halloween;

public class Zombi extends Monstruo{
    private int cerebrosComidos;
    public Zombi(String nombre, double costeInvocacion) {
        super(nombre, costeInvocacion);
        this.cerebrosComidos = 0;
    }

    public void comerCerebros(){
        do {
            int c = (int) (Math.random() * 10 + 1);
            if (c!=10){
                cerebrosComidos++;
                System.out.println("Has comido "+cerebrosComidos+" cerebros");
            } else {
                setEstaVivo(false);
                System.out.println("Has muerto");
            }
        }while (isEstaVivo());
    }
}
