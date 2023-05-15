package co.edu.umanizales.tads.model;

import co.edu.umanizales.tads.exception.ListDEException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

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
        digo que al nodo next, osea la cabeza que el previous sea igual al nuevo nodo, al previous del nuevo nodo,
        sea igual al nodo donde esta parado el temporal y al nodo next de donde este parado el tempotal sea igual
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

}
