package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;

import java.util.ArrayList;
import java.util.Random;

@Data

public class ListDECircle {
    private NodeDE headDEC;
    private int size;

    /*
    public String toString(){
        StringBuilder sb=new StringBuilder();
        NodeDE temp=this.headDEC;
        sb.append("[");
        while (temp!=null) {
            sb.append(temp.getData().toString());
            temp = temp.getNext();
            if (temp != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

     */
    public ArrayList<Pet> printList(){
        ArrayList<Pet> pets=new ArrayList<>();
        if (this.headDEC!=null){
            NodeDE temp=this.headDEC;
            do{
                pets.add(temp.getData());
                temp = temp.getNext();
            }while (temp!=this.headDEC);
        }
        return pets;
    }

    /*metodo adicionar
    pregunto a cabeza si hay datos
        si
        recorro la lista hasta el final, que el siguiente del nodo sea igual a la cabeza, cuando se cumpla esa condicion
        digo que al nodo siguiente osea la cabeza que el previous de este sea igual al nuevo nodo, al previous del nuevo
         nodo sea igual al nodo donde esta parado el temporal y al nodo next de donde este parado el tempotal sea igual
        al nuevo nodo
     */

    public void addPet(Pet pet) throws ListDEException {
        if (this.headDEC!=null){
            NodeDE temp=this.headDEC;
            while (temp.getNext()!=this.headDEC){
                if (temp.getData().getId().equals(pet.getId())) {
                    throw new ListDEException("ya existe una mascota");
                }
                temp=temp.getNext();
            }
            if (temp.getData().getId().equals(pet.getId())) {
                throw new ListDEException("ya existe una mascota");
            }
            NodeDE newNode= new NodeDE(pet);
            temp.setNext(newNode);
            newNode.setPrevi(temp);
            newNode.setNext(this.headDEC);
            this.headDEC.setPrevi(newNode);
        }else {
            NodeDE newNode=new NodeDE(pet);
            setHeadDEC(newNode);
            this.headDEC.setNext(newNode);
            this.headDEC.setPrevi(newNode);
        }
        size++;
    }

    /*adicionar al inicio
    pregunto a cabeza si tiene datos
        pregunto si siguiente a cabeza tiene datos
            no
                le digo al previous de la cabeza que tome al nuevo nodo, al next del nuevo nodo tome a la cabeza,
                al next de cabeza que tome al nuevo nodo y al previous del nuevo nodo que tome a cabeza

                despues digo que cabeza sea igual a nuevo nodo

            si
                me paro en cabeza y le digo al anterior a cabeza que su next sea igual a nuevo nodo, al previous
                 de cabeza sea igual a nuevo nodo, el previous del nuevo nodo sea igual al anterior de cabeza y el next
                 sea igual a cabeza

                 despues digo que cabeza sea igual a nuevo nodo


     */

    public void addToStartPet(Pet pet){
        NodeDE newNode=new NodeDE(pet);
        if(this.headDEC!=null){
            newNode.setPrevi(this.headDEC.getPrevi());
            newNode.setNext(this.headDEC);
            this.headDEC.getPrevi().setNext(newNode);
            this.headDEC.setPrevi(newNode);
        }
        this.headDEC=newNode;
        this.headDEC.setNext(this.headDEC);
        this.headDEC.setPrevi(this.headDEC);
        size++;
    }

    /*bañar a una mascota aleatoriamente
    pregunto a cabeza si hay datos
        si
            debo establacer el rango minimo para recorrer la lista aleatoriamente el cual sera el tamaño de lista en ese
            momento
            despues de eso recorro la lista con un contador incluido el cual amumentara en 1 cada vez que recorra un
            nodo y cuando el contador llegue al numero dado aleatoriamente (el numero sse dara con el java.util.ramdom),
            paro en ese nodo, pregunto si la variable shower es igual false si lo es cambio el estado de shower de
            false a true, sino lo es me paso al siguiente y pregunto lo mismo.
     */

    public void showerRandomlyPet() throws ListDEException{
        if (this.headDEC!=null){
            NodeDE temp=this.headDEC;
            Random random=new Random();
            int i;
            int numberandom=random.nextInt(size);
            for (i=0; i<numberandom; i++) {
                temp = temp.getNext();
            }
                if (temp.getData().getShower().equals(false)) {
                    temp.getData().setShower(true);
                }else {
                    throw new ListDEException("la mascota escogida está bañada, escoga otra");
                }
            }
        }

    }


