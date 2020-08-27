/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiQueue;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jgale
 */
public class TableModel extends DefaultTableModel {

    private String[] columnNames = {};

    public TableModel(String[] columnNames) {
        super(columnNames, 0);
        this.columnNames = columnNames;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        }
        return super.getColumnClass(columnIndex);
    }

    public void setRowsReg(Node head) {

        if (getRowCount() > 0) {
            for (int i = getRowCount() - 1; i > -1; i--) {
                removeRow(i);
            }
        }
        Node temp = head;

        if (temp != null) {
            do {
                Object[] row = new Object[4];
                row[0] = temp.getId();
                row[1] = temp.getTimeIn();
                row[2] = temp.getRaf();
                row[3] = temp.getLastList();

                super.addRow(row);
                temp = temp.getNext();
            } while (temp != head);
        }
    }

    public void setRowsQueue(Node head) {

        if (getRowCount() > 0) {
            for (int i = getRowCount() - 1; i > -1; i--) {
                removeRow(i);
            }
        }
        Node temp = head;

        if (temp != null) {
            do {
                Object[] row = new Object[3];
                row[0] = temp.getId();
                row[1] = temp.getTimeIn();
                row[2] = temp.getRaf();

                super.addRow(row);
                temp = temp.getNext();
            } while (temp != head);
        }
    }

    public void setRowsHistory(Node head) {

        if (getRowCount() > 0) {
            for (int i = getRowCount() - 1; i > -1; i--) {
                removeRow(i);
            }
        }
        Node temp = head;

        if (temp != null) {
            do {
                Object[] row = new Object[8];
                row[0] = temp.getId();
                row[1] = temp.getLastList();
                row[2] = temp.getTimeIn();
                row[3] = temp.getRafExecuted();
                row[4] = temp.getTimeStart();
                row[5] = temp.getTimeEnd();
                row[6] = temp.getTimeReturn();
                row[7] = temp.getTimeWait();

                super.addRow(row);
                temp = temp.getNext();
            } while (temp != head);
        }
    }

    public void setRowsBlock(Node head) {

        if (getRowCount() > 0) {
            for (int i = getRowCount() - 1; i > -1; i--) {
                removeRow(i);
            }
        }
        Node temp = head;

        if (temp != null) {
            do {
                Object[] row = new Object[5];
                row[0] = temp.getId();
                row[1] = temp.getTimeIn();
                row[2] = temp.getRaf();
                row[3] = temp.getRafExecuted();
                row[4] = temp.getLastList();

                super.addRow(row);
                temp = temp.getNext();
            } while (temp != head);
        }
    }

    public void addRowGantt(Node newNode, int time, String queue) {
        Object[] row = new Object[time + 3];
        row[0] = newNode.getId();
        int i = 0;
        for (i = 1; i < time; i++) {
            row[i] = "";
        }
        row[i + 1] = queue;
        super.addRow(row);
    }

    public void addColumnGantt(Node headRR, Node headSRTF, Node headFIFO, Node headBlock, Node onProcess, int time) {

        Object[] col = new Object[this.getRowCount()];
        Node tempQueue;

        for (int i = 0; i < this.getRowCount(); i++) {
            boolean found = false;

            if (onProcess != null && onProcess.getId() == (int) getValueAt​(i, 0)) {
                col[i] = "p";
                found = true;
            } else {

                tempQueue = headRR;
                if (tempQueue != null) {
                    do {
                        if (tempQueue.getId() == (int) getValueAt​(i, 0)) {
                            col[i] = "RR";
                            found = true;
                        }

                        tempQueue = tempQueue.getNext();
                    } while (tempQueue != headRR);
                }

                tempQueue = headSRTF;
                if (tempQueue != null) {
                    do {
                        if (tempQueue.getId() == (int) getValueAt​(i, 0)) {
                            col[i] = "SRTF";
                            found = true;
                        }

                        tempQueue = tempQueue.getNext();
                    } while (tempQueue != headSRTF);
                }

                tempQueue = headFIFO;
                if (tempQueue != null) {
                    do {
                        if (tempQueue.getId() == (int) getValueAt​(i, 0)) {
                            col[i] = "FIFO";
                            found = true;
                        }

                        tempQueue = tempQueue.getNext();
                    } while (tempQueue != headFIFO);
                }

                tempQueue = headBlock;
                if (tempQueue != null) {
                    do {
                        if (tempQueue.getId() == (int) getValueAt​(i, 0)) {
                            col[i] = "b";
                            found = true;
                        }

                        tempQueue = tempQueue.getNext();
                    } while (tempQueue != headBlock);
                }
            }

            if (!found) {
                col[i] = "";
            }
        }
        super.addColumn(time, col);
    }
}
