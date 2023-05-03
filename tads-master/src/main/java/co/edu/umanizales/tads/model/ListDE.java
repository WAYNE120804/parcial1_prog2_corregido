package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ListDE {
    private NodeDE headDE;
    private int size;

    public void addPet(Pet pet){
        if (this.headDE!=null){
            NodeDE temp=this.headDE;
            while (temp!=null){
                temp=temp.getNext();
            }
            NodeDE newNode= new NodeDE(pet);
            temp.setNext(newNode);
            temp.setPrevi(temp);
        }else {
            NodeDE newNode=new NodeDE(pet);
        }
        size++;
    }

    public void addToStartPet(Pet pet){
        if(this.headDE!=null){
            NodeDE temp=this.headDE;
            NodeDE newNode=new NodeDE(pet);
            temp.setPrevi(newNode);
            temp.setNext(newNode);
            this.headDE=newNode;
        }else {
            this.headDE=new NodeDE(pet);
        }
        size++;
    }

    public void deletePet(String id){
        if(this.headDE!=null){
            NodeDE temp=headDE;
            ListDE listDECp=new ListDE();
            while (temp!=null) {
                if (!temp.getNext().getData().getId().equals(id)) {
                    listDECp.addPet(temp.getData());
                    temp = temp.getNext();
                }
                temp = temp.getNext();
            }
            this.headDE=listDECp.getHeadDE();
        }

    }

    // ejercicio 1 invertir lista
    public void invertListDe() {
        if (this.headDE  != null) {
            ListDE listDECp = new ListDE();
            NodeDE temp = this.headDE;
            while (temp != null) {
                listDECp.addToStartPet(temp.getData());
                temp = temp.getNext();
            }
            this.headDE = listDECp.getHeadDE();
        }
    }

    //ejercicio 2 ordenar machos al comienzo
    public void orderMalesToStart()  {
        if (this.headDE != null) {
            ListDE listDECp = new ListDE();
            NodeDE temp = this.headDE;
            while (temp != null) {
                if (temp.getData().getGender() == 'M') {
                    listDECp.addToStartPet(temp.getData());
                } else {
                    listDECp.addPet(temp.getData());
                }

                temp = temp.getNext();
            }
            this.headDE = listDECp.getHeadDE();
        }
    }

    //ejercicio 3 intercalar macho hembra


    // ejercicios 4
    //elimina una mascota que tenga una edad determinda
    public void deletePetbyAge(byte age) {
        NodeDE temp = this.headDE;
        ListDE listDECp = new ListDE();
        if (this.headDE != null) {
            while (temp != null) {
                if (temp.getData().getAge() != age) {
                    listDECp.addToStartPet(temp.getData());
                }
                temp = temp.getNext();
            }
            this.headDE = listDECp.getHeadDE();
        }
    }

    //ejercicio 5 promedio de edades
    public float averageAge(){
        float ages=0;
        if (this.headDE != null) {
            NodeDE temp = this.headDE;
            while (temp.getNext()!=null){
                ages=ages+temp.getData().getAge();
                temp=temp.getNext();
            }
            return (float) ages/size;
        }else {
            return (int)0;
        }
    }



}
