/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiQueue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author jgale
 */
public class View extends JFrame implements ActionListener {
    
    private final JLabel lblTime;
    private final JLabel lblStatus;
    
    private final JTextField txtRaf;
    private final JTextField txtQuantum;
    private final JTextField txtAgingTime;
    private final JButton btnAddRR;
    private final JButton btnAddSRTF;
    private final JButton btnAddFIFO;
    private final JButton btnStartProcess;
    private final JButton btnBlockProcess;
    private final JButton btnUnBlockProcess;
    
    private final String[] columnNamesQueueRR;
    private final JTable tableQueueRR;
    private final TableModel tableModelQueueRR;
    private final JScrollPane scrollQueueRR;
    
    private final String[] columnNamesQueueSRTF;
    private final JTable tableQueueSRTF;
    private final TableModel tableModelQueueSRTF;
    private final JScrollPane scrollQueueSRTF;
    
    private final String[] columnNamesQueueFIFO;
    private final JTable tableQueueFIFO;
    private final TableModel tableModelQueueFIFO;
    private final JScrollPane scrollQueueFIFO;
    
    private final String[] columnNamesReg;
    private final JTable tableReg;
    private final TableModel tableModelReg;
    private final JScrollPane scrollReg;
    
    private final String[] columnNamesHistory;
    private final JTable tableHistory;
    private final TableModel tableModelHistory;
    private final JScrollPane scrollHistory;
    
    private final String[] columnNamesBlock;
    private final JTable tableBlock;
    private final TableModel tableModelBlock;
    private final JScrollPane scrollBlock;
    
    private final String[] columnNamesGantt;
    private final JTable tableGantt;
    private final TableModel tableModelGantt;
    private final JScrollPane scrollGantt;
    
    private final Scheduler processor;
    
    public View(Scheduler processor) {
        
        this.processor = processor;
        
        this.columnNamesQueueRR = new String[]{"Id", "Llegada", "Ráfaga"};
        this.columnNamesQueueSRTF = new String[]{"Id", "Llegada", "Ráfaga"};
        this.columnNamesQueueFIFO = new String[]{"Id", "Llegada", "Ráfaga"};
        this.columnNamesReg = new String[]{"Id", "Llegada", "Ráfaga", "Cola inicial"};
        this.columnNamesHistory = new String[]{"Id", "Cola final", "Llegada", "Ráfaga ejecutada", "Comienzo", "Final", "Retorno", "Espera"};
        this.columnNamesBlock = new String[]{"Id", "Llegada", "Ráfaga", "Ráfaga ejecutada", "Cola de retorno"};
        this.columnNamesGantt = new String[]{"Id"};
        
        setBounds(0, 50, 1100, 300);
        setTitle("Gestión de procesos Multicola");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        //PANEL CONTROL
        JPanel panelControlTop = new JPanel();
        add(panelControlTop);
        
        panelControlTop.add(new JLabel("Ráfaga"));
        this.txtRaf = new JTextField("", 5);
        panelControlTop.add(this.txtRaf);//rafaga

        this.btnAddRR = new JButton("Ingresar a RR");
        this.btnAddRR.addActionListener(this);
        panelControlTop.add(this.btnAddRR);//Boton añadir RR

        this.btnAddSRTF = new JButton("Ingresar a SRTF");
        this.btnAddSRTF.addActionListener(this);
        panelControlTop.add(this.btnAddSRTF);//Boton añadir SRTF

        this.btnAddFIFO = new JButton("Ingresar a FIFO");
        this.btnAddFIFO.addActionListener(this);
        panelControlTop.add(this.btnAddFIFO);//Boton añadir FIFO

        //PANEL CONTROL
        JPanel panelControlBottom = new JPanel();
        add(panelControlBottom);
        
        panelControlBottom.add(new JLabel("Tiempo de envejecimiento"));
        this.txtAgingTime = new JTextField("", 5);
        panelControlBottom.add(this.txtAgingTime);//Aging

        panelControlBottom.add(new JLabel("Quantum RR"));
        this.txtQuantum = new JTextField("", 5);
        panelControlBottom.add(this.txtQuantum);//QuantumRR

        this.btnStartProcess = new JButton("Iniciar simulación");
        this.btnStartProcess.addActionListener(this);
        panelControlBottom.add(this.btnStartProcess);//Boton iniciar

        this.btnBlockProcess = new JButton("Bloquear proceso");
        this.btnBlockProcess.addActionListener(this);
        this.btnBlockProcess.setEnabled(false);
        panelControlBottom.add(this.btnBlockProcess);//Boton bloquear

        this.btnUnBlockProcess = new JButton("Desbloquear proceso");
        this.btnUnBlockProcess.addActionListener(this);
        this.btnUnBlockProcess.setEnabled(false);
        panelControlBottom.add(this.btnUnBlockProcess);//Boton desbloquear

        //PANEL COLAS
        JPanel panelQueuesTop = new JPanel();
        panelQueuesTop.setLayout(new BoxLayout(panelQueuesTop, BoxLayout.X_AXIS));
        add(panelQueuesTop);

        //TABLA COLA RR
        JPanel panelQueueRR = new JPanel();
        panelQueueRR.setLayout(new BoxLayout(panelQueueRR, BoxLayout.Y_AXIS));
        panelQueueRR.setBackground(new Color(4, 201, 217));
        panelQueueRR.add(new JLabel("Cola de listos RR"));
        this.tableModelQueueRR = new TableModel(columnNamesQueueRR);
        this.tableQueueRR = new JTable(this.tableModelQueueRR);
        this.scrollQueueRR = new JScrollPane(this.tableQueueRR);
        panelQueueRR.add(this.scrollQueueRR);
        panelQueuesTop.add(panelQueueRR);

        //TABLA COLA SRTF
        JPanel panelQueueSRTF = new JPanel();
        panelQueueSRTF.setLayout(new BoxLayout(panelQueueSRTF, BoxLayout.Y_AXIS));
        panelQueueSRTF.setBackground(new Color(16, 240, 163));
        panelQueueSRTF.add(new JLabel("Cola de listos SRTF"));
        this.tableModelQueueSRTF = new TableModel(columnNamesQueueSRTF);
        this.tableQueueSRTF = new JTable(this.tableModelQueueSRTF);
        this.scrollQueueSRTF = new JScrollPane(this.tableQueueSRTF);
        panelQueueSRTF.add(this.scrollQueueSRTF);
        panelQueuesTop.add(panelQueueSRTF);

        //TABLA COLA FIFO
        JPanel panelQueueFIFO = new JPanel();
        panelQueueFIFO.setLayout(new BoxLayout(panelQueueFIFO, BoxLayout.Y_AXIS));
        panelQueueFIFO.setBackground(new Color(4, 217, 51));
        panelQueueFIFO.add(new JLabel("Cola de listos FIFO"));
        this.tableModelQueueFIFO = new TableModel(columnNamesQueueFIFO);
        this.tableQueueFIFO = new JTable(this.tableModelQueueFIFO);
        this.scrollQueueFIFO = new JScrollPane(this.tableQueueFIFO);
        panelQueueFIFO.add(this.scrollQueueFIFO);
        panelQueuesTop.add(panelQueueFIFO);

        //BARRA DE ESTADO
        JPanel panelStatus = new JPanel();
        add(panelStatus);
        
        this.lblTime = new JLabel("Tiempo: 000.");
        this.lblTime.setForeground(Color.white);
        this.lblTime.setFont(new Font("Arial", Font.BOLD, 13));
        panelStatus.add(this.lblTime);//lbl tiempo

        this.lblStatus = new JLabel("Estado: Detenido.");
        this.lblStatus.setForeground(Color.white);
        this.lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
        panelStatus.add(this.lblStatus);//lbl estado
        panelStatus.setBackground(new Color(84, 0, 224));

        //PANEL COLAS
        JPanel panelQueuesBottom = new JPanel();
        panelQueuesBottom.setLayout(new BoxLayout(panelQueuesBottom, BoxLayout.X_AXIS));
        add(panelQueuesBottom);

        //TABLA ENTRADAS
        JPanel panelReg = new JPanel();
        panelReg.setLayout(new BoxLayout(panelReg, BoxLayout.Y_AXIS));
        panelReg.add(new JLabel("Registro de entradas"));
        this.tableModelReg = new TableModel(columnNamesReg);
        this.tableReg = new JTable(this.tableModelReg);
        this.scrollReg = new JScrollPane(this.tableReg);
        panelReg.add(this.scrollReg);
        panelQueuesBottom.add(panelReg);

        //TABLA HISTORIAL
        JPanel panelHistory = new JPanel();
        panelHistory.setLayout(new BoxLayout(panelHistory, BoxLayout.Y_AXIS));
        panelHistory.setBackground(Color.orange);
        panelHistory.add(new JLabel("Historial"));
        this.tableModelHistory = new TableModel(columnNamesHistory);
        this.tableHistory = new JTable(this.tableModelHistory);
        this.scrollHistory = new JScrollPane(this.tableHistory);
        panelHistory.add(this.scrollHistory);
        panelQueuesBottom.add(panelHistory);

        //TABLA BLOQUEOS
        JPanel panelBlock = new JPanel();
        panelBlock.setLayout(new BoxLayout(panelBlock, BoxLayout.Y_AXIS));
        panelBlock.setBackground(Color.red);
        JLabel lblB = new JLabel("Cola de bloqueos");
        lblB.setForeground(Color.white);
        panelBlock.add(lblB);
        this.tableModelBlock = new TableModel(columnNamesBlock);
        this.tableBlock = new JTable(this.tableModelBlock);
        this.scrollBlock = new JScrollPane(this.tableBlock);
        panelBlock.add(this.scrollBlock);
        panelQueuesBottom.add(panelBlock);

        //GANTT
        JPanel panelGantt = new JPanel();
        panelGantt.setLayout(new BoxLayout(panelGantt, BoxLayout.Y_AXIS));
        panelGantt.add(new JLabel("Diagrama de Gantt"));
        panelGantt.setPreferredSize(new Dimension(100, 1000));
        this.tableModelGantt = new TableModel(columnNamesGantt);
        this.tableGantt = new JTable(this.tableModelGantt);
        this.tableGantt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.tableGantt.setDefaultRenderer(Object.class, new TableRender());
        this.scrollGantt = new JScrollPane(this.tableGantt);
        panelGantt.add(this.scrollGantt);
        
        this.tableGantt.getColumnModel().getColumn(0).setPreferredWidth(22);
        add(panelGantt);
        
        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnStartProcess) {
            try {
                this.processor.start(this.txtQuantum.getText(), this.txtAgingTime.getText());
                this.btnStartProcess.setEnabled(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Los datos de procesamiento no son correctos.");
            }
        } else if (e.getSource() == this.btnAddRR) {
            try {
                this.processor.addProcessRR(Integer.parseInt(this.txtRaf.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los datos del proceso no son correctos");
            }
        } else if (e.getSource() == this.btnAddSRTF) {
            try {
                this.processor.addProcessSRTF(Integer.parseInt(this.txtRaf.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los datos del proceso no son correctos");
            }
        } else if (e.getSource() == this.btnAddFIFO) {
            try {
                this.processor.addProcessFIFO(Integer.parseInt(this.txtRaf.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los datos del proceso no son correctos");
            }
        } else if (e.getSource() == this.btnBlockProcess) {
            this.processor.blockProcess();
        } else if (e.getSource() == this.btnUnBlockProcess) {
            this.processor.unBlockProcess();
        }
    }

    //TABLES
    public void setTableRR(Node head) {
        this.tableModelQueueRR.setRowsQueue(head);
    }
    
    public void setTableSRTF(Node head) {
        this.tableModelQueueSRTF.setRowsQueue(head);
    }
    
    public void setTableFIFO(Node head) {
        this.tableModelQueueFIFO.setRowsQueue(head);
    }
    
    public void setTableReg(Node head) {
        this.tableModelReg.setRowsReg(head);
    }
    
    public void setTableHistory(Node head) {
        this.tableModelHistory.setRowsHistory(head);
    }
    
    public void setTableBlock(Node head) {
        this.tableModelBlock.setRowsBlock(head);
    }

    //GANTT
    public void addRowGantt(Node tail, int time, String queue) {
        this.tableModelGantt.addRowGantt(tail, time, queue);
    }
    
    public void addColumnGantt(Node headRR, Node headSRTF, Node headFIFO, Node headBlock, Node processing, int time) {
        this.tableModelGantt.addColumnGantt(headRR, headSRTF, headFIFO, headBlock, processing, time);
        
        for (int i = 0; i < this.tableModelGantt.getColumnCount(); i++) {
            this.tableGantt.getColumnModel().getColumn(i).setPreferredWidth(25);
        }
    }

    //LABELS
    public void setStatus(String status) {
        this.lblStatus.setText(status);
    }
    
    public void setTime(String time) {
        this.lblTime.setText(time);
    }

    //BUTTONS
    public void enableBlockBtn() {
        this.btnBlockProcess.setEnabled(true);
    }
    
    public void disableBlockBtn() {
        this.btnBlockProcess.setEnabled(false);
    }
    
    public void enableUnBlockBtn() {
        this.btnUnBlockProcess.setEnabled(true);
    }
    
    public void disableUnBlockBtn() {
        this.btnUnBlockProcess.setEnabled(false);
    }

    //TEXTFIELDS
    public void resetTxtRaf() {
        this.txtRaf.setText("");
    }
    
    public void focusTxtRaf() {
        this.txtRaf.requestFocus();
    }
}
