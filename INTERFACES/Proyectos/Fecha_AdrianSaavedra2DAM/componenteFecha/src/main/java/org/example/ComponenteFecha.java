package org.example;

import java.awt.Color;
import java.awt.FlowLayout;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import org.example.exceptions.*;

public class ComponenteFecha extends JPanel {
    private int dia;

    private int mes;

    private int year;

    private final JComboBox<Integer> comboDay;

    private final JComboBox<Integer> comboMonth;

    private final JTextField comboYear;

    public ComponenteFecha() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.comboDay = new JComboBox<>();
        this.comboMonth = new JComboBox<>();
        this.comboYear = new JTextField("", 4);
        this.comboYear.setFont(this.comboYear.getFont().deriveFont(20.0F));
        int i;
        for (i = 1; i <= 31; i++)
            this.comboDay.addItem(i);
        for (i = 1; i <= 12; i++)
            this.comboMonth.addItem(i);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        setBorder(border);
        add(this.comboDay);
        add(this.comboMonth);
        add(this.comboYear);
    }

    public LocalDate getDate() throws FechaException {
        if (this.comboYear.getText().isEmpty())
            throw new FechaIncompletaException();

        Integer diaInt = (Integer)this.comboDay.getSelectedItem();
        Integer mesInt = (Integer)this.comboMonth.getSelectedItem();
        if (diaInt == null || mesInt == null)
            throw new FechaIncompletaException();
        this.dia = diaInt;
        this.mes = mesInt;
        this.year = Integer.parseInt(this.comboYear.getText());
        if (this.mes == 2 && (this.dia > 29 || (this.dia == 29 && !esBisiesto(this.year))))
            throw new FechaImposibleException();
        if ((this.mes == 4 || this.mes == 6 || this.mes == 9 || this.mes == 11) && this.dia == 31)
            throw new FechaIncorrectaException();
        return LocalDate.of(this.year, this.mes, this.dia);
    }

    public void setDate(int diai, int messi, int yeari) {
        this.dia = diai;
        this.mes = messi;
        this.year = yeari;
        this.comboDay.setSelectedItem(diai);
        this.comboMonth.setSelectedItem(messi);
        this.comboYear.setText(String.valueOf(yeari));
    }

    private boolean esBisiesto(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }
}
