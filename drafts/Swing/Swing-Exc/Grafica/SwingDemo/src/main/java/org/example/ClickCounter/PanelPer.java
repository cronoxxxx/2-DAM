package org.example.ClickCounter;

import javax.swing.*;
import java.awt.*;

public class PanelPer extends JPanel {
    public static final int FLOWLAYOUT = 0;
    public static final int BORDERLAYOUT = 1;
    public static final int GRIDLAYOUT = 2;

    public PanelPer(int tipo) {
        switch (tipo) {
            case FLOWLAYOUT -> setLayout(new FlowLayout(FlowLayout.CENTER));
            case BORDERLAYOUT -> setLayout(new BorderLayout());
            case GRIDLAYOUT -> setLayout(new GridLayout());
        }
    }


}
