package org.example.Propositos;

import javax.swing.*;
import java.io.*;

public class LogicaPropositos {
private static String ruta ="txt/propositos.txt";
    public static void nuevoProposito(DefaultListModel<String> listModel) {
        String proposito = JOptionPane.showInputDialog(null, "Introduce un nuevo propósito");
        if (proposito != null && !proposito.isEmpty())
            listModel.addElement(proposito);

    }

    public static void borrarProposito(DefaultListModel<String> listModel, JList<String> list) {
        if (!list.isSelectionEmpty())
            listModel.removeElementAt(list.getSelectedIndex());
    }

    public static void guardarPropositos(DefaultListModel<String> listModel) {

        try {
            FileWriter archivoWriter = new FileWriter(ruta);
            BufferedWriter bufferedWriter = new BufferedWriter(archivoWriter);
            for (int i = 0; i < listModel.getSize(); i++) {
                bufferedWriter.write(listModel.getElementAt(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            archivoWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void cargarPropositos(DefaultListModel<String> listModel) {
        try {
            FileReader archivoWriter = new FileReader(ruta);
            BufferedReader bufferedWriter = new BufferedReader(archivoWriter);

            String linea = bufferedWriter.readLine();
            while (linea != null) {
                listModel.addElement(linea);
                linea = bufferedWriter.readLine();
            }
            bufferedWriter.close();
            archivoWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }





