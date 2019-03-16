package Projekt2Memory;

import javax.swing.*;
import java.awt.*;

public class MyListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component comp = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
        switch (index) {
            case 0:
                comp.setBackground(new Color(255,233,0));
                break;
            case 1:
                comp.setBackground(new Color(192,192,192));
                break;
            case 2:
                comp.setBackground(new Color(210,105,30));
                break;
                default :
                    comp.setBackground(new Color(240,230,140));

        }
        return comp;
    }
}
