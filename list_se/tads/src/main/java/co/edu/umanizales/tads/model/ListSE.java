package co.edu.umanizales.tads.model;

import lombok.Data;

import java.util.List;

@Data
public class ListSE {
    private Node head;

    public void add(Kid kid) {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {

                temp = temp.getNext();
            }
            // para en el ultimo
            Node newNode = new Node(kid);
            temp.setNext(newNode);
        } else {
            head = new Node(kid);
        }
    }

    public void addToStart(Kid kid) {
        if (head != null) {
            Node newNode = new Node(kid);
            newNode.setNext(head);
            head = newNode;
        } else {
            head = new Node(kid);
        }


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

    //metodo para eliminar niños recibiendo el id
    public void deleteKid(String id) {
        Node temp = head;
        while (!temp.getNext().getData().getIdentification().equals(id)) {
            temp = temp.getNext();
        }
        temp.getNext().setData(null);
    }

    //adelantar en posicion
    public void advanceInpos(Kid kid, int pos) {
        Node temp = head;
        for (int i = 0; i < pos; i++) {
            temp = temp.getNext();
        }
        Node newNode = new Node(kid);
        temp.setNext(newNode);
    }


    // ejercicios 4
    //elimina un niño que tenga una edad determinda
    public void deleteKidbyAge( byte age) {
        Node temp=this.head;
        ListSE listSECp=new ListSE();
        while(temp!=null) {
            if (temp.getData().getAge() != age) {
                listSECp.addToStart(temp.getData());
                temp.getNext();
            }
        }
        this.head=listSECp.getHead();
    }

    public int getPosById(String id){
        Node temp=this.head;
        int acum=0;
        if(this.head!=null && !temp.getData().getIdentification().equals(id)){
            acum=acum+1;
            temp.getNext();
            return acum;
        }
        return acum;
    }

    //ejercio 7

    //metodo para hacer que el costal de un niño adelante posiciones con una posiciones dadas
    public void gainPositionKid(String id, int gain) {
        Node temp = head;
        gain=0;
        int sum=0;
        ListSE listSECp= new ListSE();
        if(head!=null){
            while(temp!=null && !temp.getData().getIdentification().equals(id)){
                listSECp.add(temp.getData());
                temp.getNext();
            }
        }
        sum=gain-getPosById(id);
        listSECp.addInpos(getKidById(id),sum);
    }

    public Kid getKidById(String id){
        Node temp=this.head;
        if(head!=null){
            while(temp !=null){
                temp.getNext();
                while ((!temp.getData().getIdentification().equals(id))){
                    temp.getNext();
                }

            }
            temp.getData();
        }
        return null;
    }

    public void kidStart(String gender){
        Node temp=head;
        ListSE ListCp= new ListSE();
        while(temp!=null) {
            if (gender == "m") {
                ListCp.addToStart(temp.getData());
                temp.getNext();
            }
            if(gender=="f"){
                ListCp.add(temp.getData());
                temp.getNext();
            }
        }
        head=ListCp.getHead();
    }

}




