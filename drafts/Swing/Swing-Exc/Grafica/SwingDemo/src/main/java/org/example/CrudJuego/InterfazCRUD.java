package org.example.CrudJuego;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InterfazCRUD extends JFrame {
    private JButton create,delete,update;
    private JPanel principal,botones;
    private JScrollPane panelTabla;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public InterfazCRUD() {
        super("Hello Demo");

        modeloTabla = new DefaultTableModel();
        tabla = new JTable(modeloTabla);
        panelTabla = new JScrollPane(tabla);
        principal = new JPanel(new BorderLayout());
        botones = new JPanel();


        create = new JButton("Create");
        delete = new JButton("Delete");
        update = new JButton("Update");

        this.add(principal);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        add(principal);

        decoracion();


    }

    public void abrirCRUD() {
        principal.add(panelTabla, BorderLayout.CENTER);
        principal.add(botones, BorderLayout.SOUTH);

        //listener para la tabla
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    ConectividadCRUD.actualizarConListener(modeloTabla, tabla);
                }
            }
        });

        create.addActionListener(e -> ConectividadCRUD.agregarRegistro(modeloTabla));
        delete.addActionListener(e -> ConectividadCRUD.borrarRegistro(modeloTabla, tabla));
        update.addActionListener(e -> ConectividadCRUD.actualizarRegistro(modeloTabla, tabla,null)); //null es el valor de la casilla
        botones.add(create);
        botones.add(delete);
        botones.add(update);
        montarInterfaz();
    }

    public void montarInterfaz() {
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Plataforma");
        modeloTabla.addColumn("Duracion");

        modeloTabla.addColumn("Recomendable");

        ConectividadCRUD.mostarDatos(modeloTabla);




    }

    public void decoracion(){
        Font font = new Font("Arial", Font.BOLD, 14);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setFont(font);
        tabla.setDefaultRenderer(Object.class, renderer);

        // Set the background color of the table
        tabla.setBackground(Color.WHITE);

        // Set the background color of the table header
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setFont(font);

        // Set the foreground color of the table header
        header.setForeground(Color.BLACK);
    }



}
