package co.edu.umanizales.tads.model;

import ch.qos.logback.core.joran.spi.ElementSelector;
import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import lombok.Data;

@Data
public class ListSE {
    private Node head;
    private int size;

    /*
    Algoritmo de adicionar al final
    Entrada
        un niño
    si hay datos
    si
        llamo a un ayudante y le digo que se posicione en la cabeza
        mientras en el brazo exista algo
            pasese al siguiente
        va estar ubicado en el ùltimo

        meto al niño en un costal (nuevo costal)
        y le digo al ultimo que tome el nuevo costal
    no
        metemos el niño en el costal y ese costal es la cabeza
     */
    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);

        } else {
            head = new Node(kid);
        }
        size++;
    }

    /* Adicionar al inicio
    si hay datos
    si
        meto al niño en un costal (nuevocostal)
        le digo a nuevo costal que tome con su brazo a la cabeza
        cabeza es igual a nuevo costal
    no
        meto el niño en un costal y lo asigno a la cabez
     */
    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }
        size++;
    }

    //invertir lista
    public void invert() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    // ejercicios 4
    //elimina un niño que tenga una edad determinda
    public void deleteKidbyAge(byte age) {
        Node temp = this.head;
        ListSE listSECp = new ListSE();
        while (temp != null) {
            if (temp.getData().getAge() != age) {
                listSECp.addToStart(temp.getData());
                temp.getNext();
            }
        }
        this.head = listSECp.getHead();
    }

    public int getPosById(String id) {
        Node temp = this.head;
        int acum = 0;
        if (this.head != null && !temp.getData().getIdentification().equals(id)) {
            acum = acum + 1;
            temp.getNext();
            return acum;
        }
        return acum;
    }

    //ejercio 7

    //metodo para hacer que el costal de un niño adelante posiciones con una posiciones dadas
    public void gainPositionKid(String id, int gain) {
        Node temp = head;
        gain = 0;
        int sum = 0;
        ListSE listSECp = new ListSE();
        if (head != null) {
            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                listSECp.add(temp.getData());
                temp.getNext();
            }

        }
        sum = gain - getPosById(id);
        listSECp.addInpos(getKidById(id), sum);
    }

    // metodo para hacer que un niño pierda posiciones dadas

    public void losePositionKid(String id, int lose) {
        Node temp = head;
        lose = 0;
        int sum = 0;
        ListSE listSECp = new ListSE();
        if (head != null) {
            while (temp != null && !temp.getData().getIdentification().equals(id)) {
                listSECp.add(temp.getData());
                temp.getNext();
            }

        }
        sum = lose + getPosById(id);
        listSECp.addInpos(getKidById(id), sum);
    }


    public Kid getKidById(String id) {
        Node temp = this.head;
        if (head != null) {
            while (temp != null) {
                temp.getNext();
                while ((!temp.getData().getIdentification().equals(id))) {
                    temp.getNext();
                }

            }
            temp.getData();
        }
        return null;
    }

    //metodo para añadir nuevo nodo y nuevo niño en un posicion
    public void addInpos(Kid kid, int pos) {
        Node temp = head;
        for (int i = 0; i < pos; i++) {
            temp = temp.getNext();
        }
        Node newNode = new Node(kid);
        temp.setNext(newNode);
    }

    // ordenar niños al comienzo
    public void orderBoysToStart() {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listCp.addToStart(temp.getData());
                } else {
                    listCp.add(temp.getData());
                }

                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        }
    }

    public void changeExtremes() {
        if (this.head != null && this.head.getNext() != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            //temp está en el último
            Kid copy = this.head.getData();
            this.head.setData(temp.getData());
            temp.setData(copy);
        }

    }


    public int getCountKidsBylocationCode(String code) {
        int count = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //obtener la lista de ciudad y ademas se sabra cuantos niños y niñas hay por separado
    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){
        if(head !=null){
            Node temp = this.head;
            while(temp!=null){
                if(temp.getData().getAge()>age){
                    report.updateQuantity(
                            temp.getData().getLocation().getName(),
                            temp.getData().getGender());
                }
                temp = temp.getNext();
            }
        }
    }


}
