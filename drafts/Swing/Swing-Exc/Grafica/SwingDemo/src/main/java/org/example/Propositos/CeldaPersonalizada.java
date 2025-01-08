package org.example.Propositos;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class CeldaPersonalizada extends DefaultListCellRenderer {

    private Border miBorder = new MatteBorder(0, 0, 1, 0, Color.BLUE);

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setBorder(miBorder);
        return this;
    }
}
