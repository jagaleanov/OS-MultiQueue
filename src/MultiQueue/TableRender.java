/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiQueue;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author jgale
 */
public class TableRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        Color colorRR = new Color(4, 201, 217);
        Color colorSRTF = new Color(16, 240, 163);
        Color colorFIFO = new Color(4, 217, 51);
        Color colorBlock = new Color(252, 10, 11);
        Color colorProcess = new Color(84, 0, 224);

        if (value == "p") {
            this.setOpaque(true);
            this.setBackground(colorProcess);
            this.setForeground(Color.white);
            this.setText("");
        } else if (value == "RR") {
            this.setOpaque(true);
            this.setBackground(colorRR);
            this.setForeground(Color.black);
            this.setText("");
        } else if (value == "SRTF") {
            this.setOpaque(true);
            this.setBackground(colorSRTF);
            this.setForeground(Color.black);
            this.setText("");
        } else if (value == "FIFO") {
            this.setOpaque(true);
            this.setBackground(colorFIFO);
            this.setForeground(Color.black);
            this.setText("");
        } else if (value == "b") {
            this.setOpaque(true);
            this.setBackground(colorBlock);
            this.setForeground(Color.white);
            this.setText("");
        } else {
            this.setOpaque(false);
            this.setBackground(Color.white);
            this.setForeground(Color.black);
        }

        return this;
    }
}
