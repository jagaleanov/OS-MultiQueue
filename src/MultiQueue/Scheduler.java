/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiQueue;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jgale
 */
public class Scheduler implements Runnable {

    private final View view;

    private final List queueRR;
    private final List queueSRTF;
    private final List queueFIFO;

    private final List reg;
    private final List history;
    private final List block;

    private final Thread thread;

    private Node process;
    private int id;
    private int time;
    private int quantum;
    private int agingTime;
    private int quantumCounter;

    public Scheduler() {
        this.thread = new Thread(this);//pasandole la vista q es runneable
        this.reg = new List();
        this.queueRR = new List();
        this.queueSRTF = new List();
        this.queueFIFO = new List();
        this.history = new List();
        this.block = new List();

        this.time = 0;
        this.id = 1;
        this.quantumCounter = 0;
        this.process = null;
        this.view = new View(this);
    }

    @Override
    public void run() {
        while (this.time <= 150) {
            //try {
                this.process();
                //sleep(50);
                this.time++;//incrementar tiempo
            //} catch (InterruptedException ex) {
                //Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            //}
        }
        this.view.setStatus("Estado: Finalizado.");
    }

    public void start(String quantum, String agingTime) {
        this.quantum = Integer.valueOf(quantum);
        this.agingTime = Integer.valueOf(agingTime);
        this.thread.start();//iniciar hilo (run())
    }

    public void addProcessRR(int raf) {
        this.reg.add(this.id, this.time, raf, "RR");
        this.queueRR.add(this.id, this.time, raf, "RR");
        this.id++;

        this.view.setTableReg(this.reg.getHead());
        this.view.setTableRR(this.queueRR.getHead());
        this.view.addRowGantt(this.queueRR.getTail(), this.time, "RR");

        this.view.resetTxtRaf();
        this.view.focusTxtRaf();
    }

    public void addProcessSRTF(int raf) {
        this.reg.add(this.id, this.time, raf, "SRTF");
        this.queueSRTF.add(this.id, this.time, raf, "SRTF");
        this.id++;

        this.view.setTableReg(this.reg.getHead());
        this.view.setTableSRTF(this.queueSRTF.getHead());
        this.view.addRowGantt(this.queueSRTF.getTail(), this.time, "SRTF");

        this.view.resetTxtRaf();
        this.view.focusTxtRaf();
    }

    public void addProcessFIFO(int raf) {
        this.reg.add(this.id, this.time, raf, "FIFO");
        this.queueFIFO.add(this.id, this.time, raf, "FIFO");
        this.id++;

        this.view.setTableReg(this.reg.getHead());
        this.view.setTableFIFO(this.queueFIFO.getHead());
        this.view.addRowGantt(this.queueFIFO.getTail(), this.time, "FIFO");

        this.view.resetTxtRaf();
        this.view.focusTxtRaf();
    }

    public boolean startProcessRR() {
        this.quantumCounter = 0;
        this.process = this.queueRR.removeHead();//Desencolar proceso y asignarlo a la clase

        if (this.process != null) {
            this.process.setStatus("OnProcess");
            this.view.setTableRR(this.queueRR.getHead());
            this.process.setTimeStart(this.time);//setear tiempo comienzo
            this.process.sumRafExecuted();
            this.quantumCounter++;
            return true;
        }
        return false;
    }

    public boolean startProcessSRTF() {
        this.process = this.queueSRTF.removeMinRaf();//Desencolar proceso y asignarlo a la clase

        if (this.process != null) {
            this.process.setStatus("OnProcess");
            this.view.setTableSRTF(this.queueSRTF.getHead());
            this.process.setTimeStart(this.time);//setear tiempo comienzo
            this.process.sumRafExecuted();
            return true;
        }
        return false;
    }

    public boolean startProcessFIFO() {
        this.process = this.queueFIFO.removeHead();//Desencolar proceso y asignarlo a la clase

        if (this.process != null) {
            this.process.setStatus("OnProcess");
            this.view.setTableFIFO(this.queueFIFO.getHead());
            this.process.setTimeStart(this.time);//setear tiempo comienzo
            this.process.sumRafExecuted();
            return true;
        }
        return false;
    }

    public void continueProcessRR() {
        this.process.sumRafExecuted();
        this.quantumCounter++;
    }

    public void continueProcess() {
        this.process.sumRafExecuted();
    }

    public void rollProcessRR() {
        Node temp = this.process.clone();

        temp.setStatus("Done");
        temp.setTimeEnd(temp.getRafExecuted() + temp.getTimeStart());//setear el tiempo final
        temp.setTimeReturn(temp.getTimeEnd() - temp.getTimeIn());//setear el tiempo de retorno
        temp.setTimeWait(temp.getTimeReturn() - temp.getRafExecuted());//setear el tiempo de espera

        this.history.add(temp);
        this.view.setTableHistory(this.history.getHead());
        this.process.setRaf(this.process.getRaf() - this.process.getRafExecuted());
        this.process.setRafExecuted(0);
        this.process.setTimeIn(this.time);
        this.process.setStatus("Ready");
        this.queueRR.add(this.process);
        this.view.setTableRR(this.queueRR.getHead());
        this.process = null;
    }

    public void excludeProcessSRTF() {
        Node temp = this.process.clone();

        temp.setStatus("Done");
        temp.setTimeEnd(temp.getRafExecuted() + temp.getTimeStart());//setear el tiempo final
        temp.setTimeReturn(temp.getTimeEnd() - temp.getTimeIn());//setear el tiempo de retorno
        temp.setTimeWait(temp.getTimeReturn() - temp.getRafExecuted());//setear el tiempo de espera

        this.history.add(temp);
        this.view.setTableHistory(this.history.getHead());
        this.process.setRaf(this.process.getRaf() - this.process.getRafExecuted());
        this.process.setRafExecuted(0);
        this.process.setTimeIn(this.time);
        this.process.setStatus("Ready");
        this.queueSRTF.add(this.process);
        this.view.setTableSRTF(this.queueSRTF.getHead());
        this.process = null;
    }

    public void finishProcess() {
        this.process.setStatus("Done");
        this.process.setTimeEnd(this.process.getRafExecuted() + this.process.getTimeStart());//setear el tiempo final
        this.process.setTimeReturn(this.process.getTimeEnd() - this.process.getTimeIn());//setear el tiempo de retorno
        this.process.setTimeWait(this.process.getTimeReturn() - this.process.getRafExecuted());//setear el tiempo de espera
        this.history.add(this.process);
        this.view.setTableHistory(this.history.getHead());
        this.process = null;
    }

    public void blockProcess() {
        Node temp = this.process.clone();

        temp.setStatus("Blocked");
        temp.setTimeEnd(temp.getRafExecuted() + temp.getTimeStart());//setear el tiempo final
        temp.setTimeReturn(temp.getTimeEnd() - temp.getTimeIn());//setear el tiempo de retorno
        temp.setTimeWait(temp.getTimeReturn() - temp.getRafExecuted());//setear el tiempo de espera
        this.history.add(temp);
        this.view.setTableHistory(this.history.getHead());

        this.process.setRaf(this.process.getRaf() - this.process.getRafExecuted());
        this.block.add(this.process);
        this.view.setTableBlock(this.block.getHead());
        this.process = null;
        this.view.disableBlockBtn();
        this.view.enableUnBlockBtn();
    }

    public void unBlockProcess() {
        Node dequeued = this.block.removeHead();
        dequeued.setStatus("Ready");
        dequeued.setRafExecuted(0);
        dequeued.setTimeIn(this.time + 1);
        if (dequeued.getLastList() != null) {
            switch (dequeued.getLastList()) {
                case "RR":
                    this.queueRR.add(dequeued);
                    this.view.setTableBlock(this.block.getHead());
                    this.view.setTableSRTF(this.queueRR.getHead());
                    break;
                case "SRTF":
                    this.queueSRTF.add(dequeued);
                    this.view.setTableBlock(this.block.getHead());
                    this.view.setTableSRTF(this.queueSRTF.getHead());
                    break;
                case "FIFO":
                    this.queueFIFO.add(dequeued);
                    this.view.setTableBlock(this.block.getHead());
                    this.view.setTableFIFO(this.queueSRTF.getHead());
                    break;
                default:
                    break;
            }
        }

        if (this.block.getHead() == null) {
            this.view.disableUnBlockBtn();
        }
    }

    public void process() {
        Node aged;
        this.view.setTime("Tiempo: " + this.time + ".");

        //ENVEJECIMIENTO SRTF A RR
        aged = this.queueSRTF.aging(this.agingTime, this.time);

        while (aged != null) {
            aged.setLastList("RR");
            this.queueRR.add(aged);
            this.view.setTableSRTF(this.queueSRTF.getHead());
            this.view.setTableRR(this.queueRR.getHead());

            aged = this.queueSRTF.aging(this.agingTime, this.time);
        }

        //ENVEJECIMIENTO FIFO A SRTF
        aged = this.queueFIFO.aging(this.agingTime, this.time);

        while (aged != null) {
            aged.setLastList("SRTF");
            this.queueSRTF.add(aged);
            this.view.setTableFIFO(this.queueFIFO.getHead());
            this.view.setTableSRTF(this.queueSRTF.getHead());

            aged = this.queueFIFO.aging(this.agingTime, this.time);
        }

        //PROCESAMIENTO
        if (this.process != null) {
            if (null != this.process.getLastList()) {
                switch (this.process.getLastList()) {
                    case "RR":
                        this.processOutRR();
                        break;
                    case "SRTF":
                        if(this.queueRR.isEmpty()) {
                            this.processOutSRTF();
                        }else{
                            this.excludeProcessSRTF();
                        }
                        
                        break;
                    case "FIFO":
                        this.processOutFIFO();
                        break;
                    default:
                        break;
                }
            }
        }

        if (this.process == null) {
            if (!this.queueRR.isEmpty()) {
                this.processInRR();
            } else if (!this.queueSRTF.isEmpty()) {
                this.processInSRTF();
            } else if (!this.queueFIFO.isEmpty()) {
                this.processInFIFO();
            }
        }
        try {
            sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Scheduler.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.view.addColumnGantt(
                this.queueRR.getHead(),
                this.queueSRTF.getHead(),
                this.queueFIFO.getHead(),
                this.block.getHead(),
                this.process,
                this.time);

        this.view.setTime("Tiempo: " + this.time + ".");
    }

    public void processOutRR() {
        this.view.setTime("Tiempo: " + this.time);

        //PROCESAR O LIBERAR TIEMPO EN ESTADO CRÍTICO
        if (this.process != null) {//si hay un proceso en estado critico
            if ((this.process.getRaf() - this.process.getRafExecuted()) <= 1) {
                this.view.disableBlockBtn();
            }

            if (this.process.getRaf() <= this.process.getRafExecuted()) {//si ya termino su rafaga
                this.finishProcess();
                this.view.disableBlockBtn();
                this.view.setStatus("Estado: Esperando nuevo proceso.");
            } else {//si aun necesita mas tiempo de proceso
                if (this.quantumCounter == this.quantum) {
                    this.rollProcessRR();
                    this.view.setStatus("Estado: Rotando proceso...");
                } else {
                    this.continueProcessRR();
                    this.view.setStatus("Estado: Atendiendo proceso: " + this.process.getId() + ", "
                            + "Ráfaga total: " + this.process.getRaf() + ", "
                            + "Ráfaga ejecutada: " + this.process.getRafExecuted());
                }
            }
        }
    }

    public void processInRR() {
        //AÑADIR PROCESO AL ESTADO CRÍTICO
        if (this.process == null && this.startProcessRR()) {//si no hay proceso en estado critico se intenta iniciar
            if (this.process.getRaf() > 1) {
                this.view.enableBlockBtn();
            }
            this.view.setStatus("Estado: Atendiendo proceso: " + this.process.getId() + ", "
                    + "Ráfaga total: " + this.process.getRaf() + ", "
                    + "Ráfaga ejecutada: " + this.process.getRafExecuted());
        }

        if (this.process == null) {
            this.view.setStatus("Estado: Esperando nuevo proceso.");
        }
    }

    public void processOutSRTF() {
        this.view.setTime("Tiempo: " + this.time);

        //PROCESAR O LIBERAR TIEMPO EN ESTADO CRÍTICO
        if (this.process != null) {//si hay un proceso en estado critico

            if ((this.process.getRaf() - this.process.getRafExecuted()) <= 1) {
                this.view.disableBlockBtn();
            }

            if (this.process.getRaf() <= this.process.getRafExecuted()) {//si ya termino su rafaga
                this.finishProcess();
                this.view.disableBlockBtn();
                this.view.setStatus("Estado: Esperando nuevo proceso.");
            } else {//si aun necesita mas tiempo de proceso
                Node minOnQueue = queueSRTF.getMinRaf();

                if (minOnQueue != null && minOnQueue.getRaf() < this.process.getRaf()) {
                    this.view.setStatus("Estado: Excluyendo proceso: " + this.process.getId() + ", "
                            + "Ráfaga total: " + this.process.getRaf() + ", "
                            + "Ráfaga ejecutada: " + this.process.getRafExecuted());
                    this.excludeProcessSRTF();
                } else {
                    this.continueProcess();
                    this.view.setStatus("Estado: Atendiendo proceso: " + this.process.getId() + ", "
                            + "Ráfaga total: " + this.process.getRaf() + ", "
                            + "Ráfaga ejecutada: " + this.process.getRafExecuted());
                }
            }
        }
    }

    public void processInSRTF() {
        //AÑADIR PROCESO AL ESTADO CRÍTICO
        if (this.process == null && this.startProcessSRTF()) {//si no hay proceso en estado critico se intenta iniciar
            if (this.process.getRaf() > 1) {
                this.view.enableBlockBtn();
            }
            this.view.setStatus("Estado: Atendiendo proceso: " + this.process.getId() + ", "
                    + "Ráfaga total: " + this.process.getRaf() + ", "
                    + "Ráfaga ejecutada: " + this.process.getRafExecuted());
        }

        if (this.process == null) {
            this.view.setStatus("Estado: Esperando nuevo proceso.");
        }
    }

    public void processOutFIFO() {
        this.view.setTime("Tiempo: " + this.time);

        //PROCESAR O LIBERAR PROCESO EN ESTADO CRÍTICO
        if (this.process != null) {//si hay un proceso en estado critico

            if ((this.process.getRaf() - this.process.getRafExecuted()) <= 1) {
                this.view.disableBlockBtn();
            }

            if (this.process.getRaf() <= this.process.getRafExecuted()) {//si ya termino su rafaga
                this.finishProcess();
                this.view.disableBlockBtn();
                this.view.setStatus("Estado: Esperando nuevo proceso.");
            } else {//si aun necesita mas tiempo de proceso
                this.continueProcess();
                this.view.setStatus("Estado: Atendiendo proceso: " + this.process.getId() + ", "
                        + "Ráfaga total: " + this.process.getRaf() + ", "
                        + "Ráfaga ejecutada: " + this.process.getRafExecuted());
            }
        }
    }

    public void processInFIFO() {
        //AÑADIR PROCESO AL ESTADO CRÍTICO
        if (this.process == null && this.startProcessFIFO()) {//si no hay proceso en estado critico se intenta iniciar un siguiente proceso
            if (this.process.getRaf() > 1) {
                this.view.enableBlockBtn();
            }
            this.view.setStatus("Estado: Atendiendo proceso: " + this.process.getId() + ", "
                    + "Ráfaga total: " + this.process.getRaf() + ", "
                    + "Ráfaga ejecutada: " + this.process.getRafExecuted());
        }

        if (this.process == null) {
            this.view.setStatus("Estado: Esperando nuevo proceso.");
        }
    }

    public static void main(String[] args) {
        Scheduler sched = new Scheduler();
    }
}
