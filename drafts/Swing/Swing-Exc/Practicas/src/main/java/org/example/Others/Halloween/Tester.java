package org.example.Others.Halloween;

public class Tester {

    public static void main(String[] args) {
        Invocador invocador = new Invocador(100);

        Zombi zombi = (Zombi) invocador.invocar(new Zombi("Zombi", 1));
        zombi.comerCerebros();
        Esqueleto esqueleto = (Esqueleto) invocador.invocar(new Esqueleto("Esqueleto", 1));
        esqueleto.lanzarHuesos(10);
    }
}
