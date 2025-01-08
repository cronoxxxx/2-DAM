package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
@Getter@Setter
public class Exploracion {
    private JDialog marco;
    private JPanel panelPrincipal, panelInferior, panelSuperior, panelMonstruo, panelMonstruoSec;
    private JButton botAtacar, botHuir;
    private JTextArea infoExploracion;
    private JScrollPane barraDes;
    private JLabel etNombre, etOro, etNivel, etExp, etAtributos;
    private Personaje personaje;
    private Monstruo monstruo;
    @Getter
    private static int numExploracion = 0,incrementarDificultad=5;
    private boolean esJefe = false;


    public Exploracion(VentanaPrincipal ventanaPrincipal) {

        this.monstruo = null;
        this.marco = new JDialog(ventanaPrincipal.getFrame(),"Título", true);
        this.panelPrincipal = new JPanel(new BorderLayout());
        this.panelInferior = new JPanel();
        this.panelSuperior =ventanaPrincipal.getPanelSuperior();
        this.panelMonstruo = new JPanel();
        this.panelMonstruoSec = new JPanel();
        this.botAtacar = new JButton("Atacar");
        this.botHuir = new JButton("Huir");
        this.infoExploracion = new JTextArea();
        this.barraDes = new JScrollPane(infoExploracion);
        barraDes.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.personaje = ventanaPrincipal.getPersonaje();



    }





    public void comenzarExploracion() {
        decidirDificultad();
        montarInterfaz();
    }

    private void montarInterfaz() {

        panelPrincipal.add(barraDes, BorderLayout.CENTER);
        panelSuperior.setPreferredSize(new Dimension(600, 50));


        panelMonstruoSec.add(monstruo.getEtNombre());
        panelMonstruoSec.add(monstruo.getBarraVida());
        panelMonstruo.setLayout(new BoxLayout(panelMonstruo, BoxLayout.Y_AXIS));
        panelMonstruo.add(monstruo.getImagen());
        panelMonstruo.add(panelMonstruoSec);
        botAtacar.addActionListener(e -> atacar());
        botHuir.addActionListener(e -> this.marco.dispose());

        if(esJefe){
            botHuir.setEnabled(false);
        }
        panelInferior.add(botAtacar);
        panelInferior.add (new JLabel("               "));
        panelInferior.add(botHuir);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        panelPrincipal.add(panelMonstruo, BorderLayout.EAST);

        marco.add(panelPrincipal);

        marco.setSize(600, 500);
        marco.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        marco.setLocationRelativeTo(null);

        marco.setTitle("MiniRPG");
        marco.setVisible(true);
        marco.setModal(true);

        marco.setLocationRelativeTo(null);



    }

    private void atacar() {
        int damage;


        personaje.atacar(monstruo);
        infoExploracion.setText(infoExploracion.getText() + personaje.getNombre() + " ataca a " + monstruo.getNombre() + "con un ataque de " + personaje.getAtaque() + "\n");
        damage = personaje.getAtaque() - monstruo.getDefensa();
        if (damage <= 0) {
            damage = 1;
        }
        infoExploracion.setText(infoExploracion.getText() + monstruo.getNombre() + "ha recibido " + damage + " puntos de un ataque de " + personaje.getAtaque() + "\n");
        monstruo.establecerBarraVida(monstruo.getVida() );

        if (!monstruo.isVivo()) {
            personaje.setOro(monstruo.getPremioOro() + personaje.getOro());
            if (numExploracion%incrementarDificultad==0){
                personaje.subirNivel();
                incrementarDificultad+=5;
            }
            botAtacar.setEnabled(false);
            enemigoDerrotado();
        } else {
            monstruo.atacar(personaje);
            infoExploracion.setText(infoExploracion.getText() + monstruo.getNombre() + " ataca a " + personaje.getNombre() + " con un ataque de " + monstruo.getAtaque() + "\n");
            damage = monstruo.getAtaque() - personaje.getDefensa();
            if (damage <= 0) {
                damage = 1;
            }
            infoExploracion.setText(infoExploracion.getText() + personaje.getNombre() + " ha recibido " + damage + " puntos de un ataque de " + monstruo.getAtaque() + "\n");
            personaje.establecerBarraVida(personaje.getVida());

            if (!personaje.isVivo()) {
                botAtacar.setEnabled(false);
                personajeDerrotado();
            }
        }





    }

    private void personajeDerrotado() {
        VentanaFinal ventanaFinal = new VentanaFinal(VentanaFinal.DERROTA, personaje);
        ventanaFinal.abrir();
    }

    private void enemigoDerrotado() {
        VentanaFinal ventanaFinal = new VentanaFinal(VentanaFinal.VICTORIA, personaje);
        ventanaFinal.abrir();

    }

    private void decidirDificultad() {
        int dificultad = (int) (Math.random() * 100) + numExploracion;
        numExploracion++;
        monstruo = Monstruo.generarMonstruo(dificultad); //crear monstruo

        if (monstruo.getNombre().equals("Dragon")) {
            esJefe = true;
        }

    }

    public static void setNumExploracion(int numExploracion) {
        Exploracion.numExploracion = numExploracion;
    }

}
