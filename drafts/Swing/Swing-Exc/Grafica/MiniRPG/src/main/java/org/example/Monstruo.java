package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
@Getter@Setter
public class Monstruo extends Entidad {
    private int premioOro,premioExp;
    private JLabel etNombre, imagen;
    private static String[] nombresFacil = {"1","2","3"};
    private static String[] nombresMedio = {"4","5","6"};
    private static String[] nombresDificil = {"7","8","9"};

    public Monstruo(String nombre, int ataque, int defensa, double vidaMax, String dificultad) {
        super(nombre, ataque, defensa, vidaMax);

        this.etNombre = new JLabel(nombre);
        etNombre.setFont(new Font("Arial", Font.BOLD, 20));
        // Cargar la imagen desde la ruta especificada
        String rutaImagen = "C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\" + nombre + ".png";
        ImageIcon originalIcon = new ImageIcon(rutaImagen);

        // Redimensionar la imagen (por ejemplo, a 100x100 píxeles)
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Crear y configurar el JLabel para la imagen
        this.imagen = new JLabel(scaledIcon);
        switch (dificultad) {
            case "facil":
                this.premioOro = (int)(Math.random()*10+1); // aumentar el valor de premioOro
                this.premioExp = (int)(Math.random()*5+1); // aumentar el valor de premioExp
                etNombre.setForeground(Color.RED);
                break;
            case "medio":
                this.premioOro = (int)(Math.random()*15+5); // aumentar el valor de premioOro
                this.premioExp = (int)(Math.random()*25+1); // aumentar el valor de premioExp
                etNombre.setForeground(Color.BLUE);
                break;
            case "dificil":
                this.premioOro = (int)(Math.random()*30+10); // aumentar el valor de premioOro
                this.premioExp = (int)(Math.random()*50+15); // aumentar el valor de premioExp
                etNombre.setForeground(Color.DARK_GRAY);
                break;
            default:
                this.premioOro = 1000;
                this.premioExp = 500;
                break;
        }
    }

    public static Monstruo generarMonstruo(int dificultad) {
        Monstruo monstruo = null;
        int nMonstruo = (int) (Math.random() * 3);

        String nombre;
        if (dificultad < 80) {
            int ataque = (int) (Math.random() * 10 + 5); // aumentar el valor de ataque
            int defensa = (int) (Math.random() * 5 + 2); // aumentar el valor de defensa
            double vidaMax = Math.random() * 20; // aumentar el valor de vidaMax
            monstruo = new Monstruo(nombresFacil[nMonstruo], ataque, defensa, vidaMax, "facil");
        } else if (dificultad < 140) {
            int ataque = (int) (Math.random() * 15 + 10); // aumentar el valor de ataque
            int defensa = (int) (Math.random() * 10 + 5); // aumentar el valor de defensa
            double vidaMax = Math.random() * 30; // aumentar el valor de vidaMax
            monstruo = new Monstruo(nombresMedio[nMonstruo], ataque, defensa, vidaMax, "medio");
        } else if (dificultad < 200) {
            int ataque = (int) (Math.random() * 20 + 15); // aumentar el valor de ataque
            int defensa = (int) (Math.random() * 15 + 10); // aumentar el valor de defensa
            double vidaMax = Math.random() * 40; // aumentar el valor de vidaMax
            monstruo = new Monstruo(nombresDificil[nMonstruo], ataque, defensa, vidaMax, "dificil");
        } else {
            int ataque = (int) (Math.random() * 30 + 20); // aumentar el valor de ataque
            int defensa = (int) (Math.random() * 20 + 15); // aumentar el valor de defensa
            double vidaMax = Math.random() * 50; // aumentar el valor de vidaMax
            monstruo = new Monstruo("Dragon", ataque, defensa, vidaMax, "jefe");
        }
        return monstruo;
    }
}




