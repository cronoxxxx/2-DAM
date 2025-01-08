package org.example.CrudJuego;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class ConectividadCRUD {
    private static Connection miCon;
    private static Statement miSt;
    private static Object nuevoValor;

    public static void conectar(){
        try {
            miCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/prueba", "root", "doge7777xd");
            miSt = miCon.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void desconectar(){
        try {
            miSt.close();
            miCon.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostarDatos(DefaultTableModel modelo){
        conectar();


        try {
            ResultSet miRs = miSt.executeQuery("SELECT * FROM videojuegosacabados");
            modelo.setRowCount(0); //limpia la tabla
            while (miRs.next()){
                Object[] fila = {miRs.getInt(1),miRs.getString(2),miRs.getString(3),miRs.getInt(4),miRs.getBoolean(5)};
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        desconectar();
    }

    public static void agregarRegistro(DefaultTableModel modelo){    //metodo para insertar datos
        Object[] registro = new Object[4];
        String[] columnas = {"nombre","plataforma","duracion","recomendable"};
        int j=0;
        for (int i = 0; i < registro.length; i++) {
            registro[i] = JOptionPane.showInputDialog(null, STR."Introduce \{columnas[j]}");
            j++;
        }

        registro[3] = registro[3].equals("true") ? 1 : 0;

        conectar();

        try {
            miSt.execute("INSERT INTO videojuegosacabados (nombre, plataforma, duracion, recomendable) " +
                    "VALUES ('" + registro[0] + "', '" + registro[1] + "', '" + registro[2] + "', '" + registro[3] + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        desconectar();
        mostarDatos(modelo);

    }

    public static void borrarRegistro(DefaultTableModel modelo, JTable tabla) {
        int[] filasSeleccionadas = tabla.getSelectedRows();
        if (filasSeleccionadas.length > 0) {
            conectar();
            try {
                for (int i = filasSeleccionadas.length - 1; i >= 0; i--) {
                    String nombre = modelo.getValueAt(filasSeleccionadas[i], 1).toString(); // columna 1 es donde está el nombre
                    miSt.execute("DELETE FROM videojuegosacabados WHERE nombre = '" + nombre + "'");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            desconectar();
            mostarDatos(modelo);
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione al menos una fila para borrar.");
        }
    }

    public static void actualizarRegistro(DefaultTableModel modelo, JTable tabla, Object valorDeCasilla) {
        int fila = tabla.getSelectedRow();
        if (fila!=-1) {
            int columna = tabla.getSelectedColumn();
            String nombreJuego = (String) modelo.getValueAt(fila, 1);
            if (valorDeCasilla == null) {
                nuevoValor = JOptionPane.showInputDialog(null, "Introduzca el nuevo valor ");
            } else {
                nuevoValor = valorDeCasilla;
            }

            if (nuevoValor != null && !nuevoValor.equals("")) {
                if (columna == 4) { // columna 4 es recomendable
                    nuevoValor = nuevoValor.equals("true") ? 1 : 0;
                }
                conectar();
                try {
                    miSt.execute("UPDATE videojuegosacabados SET " + tabla.getColumnName(columna) + " = '" + nuevoValor + "' WHERE nombre = '" + nombreJuego + "'");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                desconectar();
                mostarDatos(modelo);
            }
        }
    }

    public static void actualizarConListener(DefaultTableModel modelo, JTable tabla) {
        int fila = tabla.getSelectedRow();
        int columna = tabla.getSelectedColumn();

        Object valorDeCasilla = tabla.getValueAt(fila, columna);

        actualizarRegistro(modelo, tabla, valorDeCasilla);
    }


}
