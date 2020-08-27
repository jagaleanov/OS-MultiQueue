/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiQueue;

/**
 *
 * @author jgale
 */
public class List {

    private Node head;

    public List() {
        this.head = null;
    }

    public void add(int id, int timeIn, int raf, String listName) {

        Node newNode = new Node(id, timeIn, raf, listName);

        if (this.head != null) {
            newNode.setNext(this.head);
            Node tail = getTail();
            tail.setNext(newNode);
        } else {
            this.head = newNode;
            this.head.setNext(this.head);
        }
    }

    public void add(Node newNode) {

        if (this.head != null) {
            newNode.setNext(this.head);
            Node tail = getTail();
            tail.setNext(newNode);
        } else {
            this.head = newNode;
            this.head.setNext(this.head);
        }
    }

    public Node removeMinRaf() {
        Node removed = this.head;
        Node prev = null;

        if (this.head != null) {
            Node temp = this.head;

            while (temp.getNext() != this.head) {
                if (temp.getNext().getRaf() < removed.getRaf()) {
                    prev = temp;
                    removed = temp.getNext();
                }
                temp = temp.getNext();
            }

            if (removed == this.head && removed.getNext() == removed) {//es unico
                removed.setNext(null);
                this.head = null;
            } else if (removed == this.head) {//es el primero
                Node tail = getTail();
                this.head = removed.getNext();
                tail.setNext(this.head);
                removed.setNext(null);
            } else {//esta en algun punto de la cola
                prev.setNext(removed.getNext());
                removed.setNext(null);
            }
        }
        return removed;
    }

    public Node removeHead() {
        Node removed = this.head;

        if (this.head != null) {

            if (this.head.getNext() == this.head) {
                removed.setNext(null);
                this.head = null;
            } else {
                Node tail = getTail();
                this.head = this.head.getNext();
                tail.setNext(this.head);
                removed.setNext(null);
            }
        }
        return removed;
    }

    public Node getHead() {
        return this.head;
    }

    public Node getTail() {
        Node tail = this.head;

        if (tail != null) {
            while (tail.getNext() != this.head) {
                tail = tail.getNext();
            }
        }

        return tail;
    }

    public Node getMinRaf() {
        Node removed = this.head;
        Node prev = null;

        if (this.head != null) {
            Node temp = this.head;

            while (temp.getNext() != this.head) {
                if (temp.getNext().getRaf() < removed.getRaf()) {
                    prev = temp;
                    removed = temp.getNext();
                }
                temp = temp.getNext();
            }
        }
        return removed;
    }

    public int getLength() {

        Node temp = this.head;
        int counter = 0;
        if (temp != null) {
            do {
                counter++;
                temp = temp.getNext();
            } while (temp != this.head);
        }
        return counter;
    }

    public Node aging(int agingTime, int actualTime) {
        Node prev = null;

        if (this.head != null) {
            Node temp = this.head;
            do {
                int age = actualTime - temp.getTimeIn();
                if (age != 0 && age % agingTime == 0) {
                    if (temp == this.head && temp.getNext() == temp) {//es unico
                        temp.setNext(null);
                        this.head = null;
                        return temp;
                    } else if (temp == this.head) {//es el primero
                        Node tail = getTail();
                        this.head = temp.getNext();
                        tail.setNext(this.head);
                        temp.setNext(null);
                        return temp;
                    } else {//esta en algún punto de la cola
                        prev.setNext(temp.getNext());
                        temp.setNext(null);
                        return temp;
                    }
                }
                prev = temp;
                temp = temp.getNext();
            } while (temp != this.head);
        }
        return null;
    }

    public boolean isEmpty() {
        return this.head == null;
    }

    public void print() {
        Node q = this.head;
        if (q != null) {
            do {
                System.out.print(q.getId() + " -> ");
                q = q.getNext();
            } while (q != this.head);
        }
        System.out.println();
    }
}
