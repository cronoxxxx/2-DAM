package org.example;

import javax.swing.*;
import java.awt.*;

public class Tienda {
    private JDialog marco;
    private JPanel panelPrincipal, panelInferior, panelSuperior, panelTienda;
    private JPanel panelEspada, panelPocion, panelEscudo, panelMapa;
    private JLabel imagenEspada, imagenEscudo, imagenPocion, imageMapa;
    private JLabel descEspada, descEscudo, descPocion, descMapa;
    private static JButton botComprarEspada, botComprarEscudo, botComprarPocion, botComprarMapa;
    private JButton salir;
    private VentanaPrincipal ventanaPrincipal;
    private Personaje personaje;
    private static boolean agotadoPocion = false, agotadoEscudo = false, agotadoEspada = false, agotadoMapa = false;

    public Tienda(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.personaje = ventanaPrincipal.getPersonaje();
        this.marco = new JDialog();
        this.panelSuperior = ventanaPrincipal.getPanelSuperior();
        this.panelPrincipal = new JPanel(new BorderLayout());
        this.panelInferior = new JPanel();
        this.marco = new JDialog(ventanaPrincipal.getFrame(),"Título", true);
        this.panelTienda = new JPanel(new GridLayout(2, 2));
        this.panelEspada = new JPanel();
        this.panelPocion = new JPanel();
        this.panelEscudo = new JPanel();
        this.panelMapa = new JPanel();

        this.imagenEspada = new JLabel(new ImageIcon(getScaledImage("C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\espada.png", 150, 150)));
        this.imagenEscudo = new JLabel(new ImageIcon(getScaledImage("C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\escudo.jpg", 150, 150)));
        this.imagenPocion = new JLabel(new ImageIcon(getScaledImage("C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\pocion.png", 150, 150)));
        this.imageMapa = new JLabel(new ImageIcon(getScaledImage("C:\\Users\\Adrian\\Desktop\\2DAM\\drafts\\Grafica\\MiniRPG\\assets\\mapa.jpg", 150, 150)));
        this.descEspada = new JLabel("Espada - 100 oro");
        this.descEscudo = new JLabel("Escudo - 100 oro");
        this.descPocion = new JLabel("Pocion - 50 oro");
        this.descMapa = new JLabel("Mapa - 40 oro");
        botComprarEspada = new JButton("Comprar");
        botComprarEscudo = new JButton("Comprar");
        botComprarPocion = new JButton("Comprar");
        botComprarMapa = new JButton("Comprar");
        this.salir = new JButton("Salir");
    }

    public Image getScaledImage(String imagePath, int width, int height) {
        Image image = new ImageIcon(imagePath).getImage();
        return image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    public void abrirTienda() {
        montarInterfaz();
        marco.setVisible(true);
    }

    private void montarInterfaz() {


        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        addObjeto(panelEspada, imagenEspada, descEspada, botComprarEspada, "espada", agotadoEspada);
        addObjeto(panelEscudo, imagenEscudo, descEscudo, botComprarEscudo, "escudo", agotadoEscudo);
        addObjeto(panelPocion, imagenPocion, descPocion, botComprarPocion, "pocion", agotadoPocion);
        addObjeto(panelMapa, imageMapa, descMapa, botComprarMapa, "mapa", agotadoMapa);

        panelPrincipal.add(panelTienda, BorderLayout.CENTER);
        salir.addActionListener(e -> marco.dispose());
        panelInferior.add(salir);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        marco.add(panelPrincipal);
        marco.setTitle("Tienda");
        marco.setSize(800, 600);
        marco.setLocationRelativeTo(null);
        marco.setModal(true);


    }

    private void addObjeto(JPanel panObjeto, JLabel imagen, JLabel desc, JButton botComprar, String nombre, boolean agotado) {

        imagen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        desc.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botComprar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        imagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        botComprar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panObjeto.setLayout(new BoxLayout(panObjeto, BoxLayout.Y_AXIS));
        panObjeto.add(imagen);
        panObjeto.add(desc);
        panObjeto.add(botComprar);
        if (agotado) {
            botComprar.setEnabled(false);
        }
        botComprar.addActionListener(e -> comprarObjeto(botComprar, nombre));

        if (panObjeto == panelEspada) {
            panObjeto.setBorder(BorderFactory.createTitledBorder("Espada"));
        } else if (panObjeto == panelEscudo) {
            panObjeto.setBorder(BorderFactory.createTitledBorder("Escudo"));
        } else if (panObjeto == panelPocion) {
            panObjeto.setBorder(BorderFactory.createTitledBorder("Pocion"));
        } else if (panObjeto == panelMapa) {
            panObjeto.setBorder(BorderFactory.createTitledBorder("Mapa"));
        }
        panelTienda.add(panObjeto);


    }

    private void comprarObjeto(JButton botComprar, String nombre) {
        switch (nombre) {
            case "espada" -> {
                if (personaje.getOro() >= 100) {
                    personaje.setAtaque(personaje.getAtaque() + 3);
                    ventanaPrincipal.getEtAtributos().setText("Atacante: " + personaje.getAtaque() + " Defensa: " + personaje.getDefensa() + " Vida: " + personaje.getVida() + "/" + personaje.getVidaMax());
                    personaje.setOro(personaje.getOro() - 100);
                    ventanaPrincipal.getEtOro().setText("Oro: " + personaje.getOro());
                    botComprar.setEnabled(false);
                    agotadoEspada = true;
                }
            }
            case "escudo" -> {
                if (personaje.getOro() >= 100) {
                    personaje.setDefensa(personaje.getDefensa() + 1);
                    ventanaPrincipal.getEtAtributos().setText("Atacante: " + personaje.getAtaque() + " Defensa: " + personaje.getDefensa() + " Vida: " + personaje.getVida() + "/" + personaje.getVidaMax());
                    personaje.setOro(personaje.getOro() - 100);
                    ventanaPrincipal.getEtOro().setText("Oro: " + personaje.getOro());
                    botComprar.setEnabled(false);
                    agotadoEscudo = true;
                }

            }
            case "pocion" -> {
                if (personaje.getOro() >= 50) {
                    personaje.setVida((int)personaje.getVidaMax());
                    personaje.establecerBarraVida(personaje.getVida());
                    ventanaPrincipal.getEtAtributos().setText("Atacante: " + personaje.getAtaque() + " Defensa: " + personaje.getDefensa() + " Vida: " + personaje.getVida() + "/" + personaje.getVidaMax());
                    personaje.setOro(personaje.getOro() - 50);
                    ventanaPrincipal.getEtOro().setText("Oro: " + personaje.getOro());
                    botComprar.setEnabled(false);
                    agotadoPocion = true;
                }

            }

            case "mapa" -> {
                if (personaje.getOro() >= 10) {
                    Exploracion.setNumExploracion(250);
                    personaje.setOro(personaje.getOro() - 10);
                    botComprar.setEnabled(false);
                    agotadoMapa = true;
                }

            }
        }
    }}


