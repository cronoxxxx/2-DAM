package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

@Getter@Setter
public class VentanaPrincipal {
    private JFrame frame;
    private JPanel panelPrincipal, panelInferior, panelSuperior;
    private JLabel etNombre, etOro, etNivel, etExp, etAtributos, etImagen;
    private JButton botExplorar,botTienda;
    private JProgressBar barraVida;
    private Personaje personaje;

    public VentanaPrincipal(Personaje personaje) {
        this.personaje = personaje;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("MiniRPG");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
barraVida = personaje.getBarraVida();
        panelPrincipal = new JPanel(new BorderLayout());
        panelInferior = new JPanel();
        panelSuperior = new JPanel();
        etNombre = new JLabel(personaje.getNombre());
        etOro = new JLabel("Oro: " + personaje.getOro());
        etNivel = new JLabel("Nivel: " + personaje.getNivel());
        etExp = new JLabel("Experiencia: " + personaje.getExp() + "/" + personaje.getExperienciaNecesaria());
        etAtributos = new JLabel("Atacante: " + personaje.getAtaque() + " Defensa: " + personaje.getDefensa() + " Vida: " + personaje.getVida() + "/" + personaje.getVidaMax());
        etImagen = new JLabel();
        botExplorar = new JButton("Explorar");
        botTienda = new JButton("Tienda");
    }

    public void comenzarJuego() {
        montarEscena();
        frame.setVisible(true);
    }

    private void montarEscena() {

        modificarFuentes();
        //Panel superior
        panelSuperior.add(etNombre);
        panelSuperior.add(etOro);
        panelSuperior.add(etNivel);
        panelSuperior.add(etExp);
        panelSuperior.add(etAtributos);
        panelSuperior.add(barraVida);

        //Panel inferior
        botExplorar.addActionListener(e -> nuevaExploracion());
        panelInferior.add(botExplorar);
        botTienda.addActionListener(e -> nuevaTienda());
        panelInferior.add(botTienda);

        //Imagen

        etImagen.setIcon(new ImageIcon("C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\castillo.jpg"));
        panelPrincipal.add(etImagen, BorderLayout.CENTER);

        //Panel principal

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        frame.add(panelPrincipal);
    }

    private void nuevaTienda() {
        Tienda tienda = new Tienda(this);
        tienda.abrirTienda();
        //solucion de bug de fuera de pantalla
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        frame.repaint();

    }


    public void refreshUI() {
        panelPrincipal.remove(panelSuperior);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();
    }

    private void modificarFuentes() {
        etNombre.setFont(new Font("Arial", Font.BOLD, 20));
        etOro.setFont(new Font("Arial", Font.BOLD, 10));
        etNivel.setFont(new Font("Arial", Font.BOLD, 10));
        etExp.setFont(new Font("Arial", Font.BOLD, 10));
        etAtributos.setFont(new Font("Arial", Font.BOLD, 10));
        etImagen.setFont(new Font("Arial", Font.BOLD, 10));
        botExplorar.setFont(new Font("Arial", Font.BOLD, 10));
    }

    private void nuevaExploracion() {
        DecimalFormat df = new DecimalFormat("#.##");
        Exploracion exploracion = new Exploracion(this);
        exploracion.comenzarExploracion();
        //solucion de bug de fuera de pantalla
        etOro.setText("Oro: " + exploracion.getPersonaje().getOro());
        etNivel.setText("Nivel: " + exploracion.getPersonaje().getNivel());
        etExp.setText("Experiencia: " + exploracion.getPersonaje().getExp() + "/" + exploracion.getPersonaje().getExperienciaNecesaria());
        etAtributos.setText("Atacante: "+ exploracion.getPersonaje().getAtaque() + " Defensa: " + exploracion.getPersonaje().getDefensa() + " Vida: " + exploracion.getPersonaje().getVida() + "/" + df.format(exploracion.getPersonaje().getVidaMax()));  ;

        refreshUI();
    }

    public static void main(String[] args) {
        Personaje personaje = new Personaje("Adrian", 10, 10, 100);
        VentanaPrincipal ventana = new VentanaPrincipal(personaje);
        ventana.comenzarJuego();
    }
}
