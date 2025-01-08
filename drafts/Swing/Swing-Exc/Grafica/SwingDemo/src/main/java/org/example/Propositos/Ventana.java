package org.example.Propositos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Ventana extends JFrame {
    private JPanel panelPrincipal,panBotones;
    private JList<String> lista;
    private DefaultListModel<String> modelo;
    private JScrollPane scroll;
    private JButton borrar,agregar; //boton para borrar elementos de la lista

    public Ventana() {
        super("Ejemplo de JList");
        panelPrincipal = new JPanel(new BorderLayout());
        panBotones = new JPanel();
        modelo = new DefaultListModel<>();
        lista = new JList<>(modelo);
        lista.setCellRenderer(new CeldaPersonalizada());
        scroll = new JScrollPane(lista);
        borrar = new JButton("Borrar");
        agregar = new JButton("Agregar");


        montarEscena();
        LogicaPropositos.cargarPropositos(modelo);
    }

    private void montarEscena(){
        agregar.addActionListener(e -> {
            LogicaPropositos.nuevoProposito(modelo);
        });

        borrar.addActionListener(e -> {
            LogicaPropositos.borrarProposito(modelo, lista);
        });

        panBotones.add(borrar);
        panBotones.add(agregar);
        panelPrincipal.add(panBotones, BorderLayout.SOUTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);


        this.setTitle("Ejemplo de JList");
        this.add(panelPrincipal);
        this.setResizable(false);
        this.setSize(1200, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        abrirVentana();

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                LogicaPropositos.guardarPropositos(modelo);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void abrirVentana(){
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Ventana();
    }


}
