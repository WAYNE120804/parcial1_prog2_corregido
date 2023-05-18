package co.edu.umanizales.tads.model;


import co.edu.umanizales.tads.controller.dto.ReportPetsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import lombok.Data;
import java.util.ArrayList;

@Data
public class ListDE {
    private NodeDE headDE;
    private int size;

    public ArrayList<Pet> printList(){
        ArrayList<Pet> pets=new ArrayList<>();
        if (this.headDE!=null){
            NodeDE temp=this.headDE;
            do{
                pets.add(temp.getData());
                temp = temp.getNext();
            }while (temp!=null);
        }
        return pets;
    }


    public void addPet(Pet pet) throws ListDEException {
        if (this.headDE!=null){
            NodeDE temp=this.headDE;
            while (temp.getNext()!=null){
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
        }else {
            NodeDE newNode=new NodeDE(pet);
            setHeadDE(newNode);
        }
        size++;
    }

    public void addToStartPet(Pet pet){
        NodeDE newNode=new NodeDE(pet);
        if(this.headDE!=null){
            this.headDE.setPrevi(newNode);
            newNode.setNext(this.headDE);
        }
        this.headDE=newNode;
        size++;
    }

    public void deletePet(String id)throws ListDEException{
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

    //metodo para añadir nuevo nodo y nueva mascota en un posicion
    public void addPetInPos(Pet pet, int pos2) throws ListDEException {
        NodeDE newNode = new NodeDE(pet);
        if (pos2 > size) {
            addPet(pet);
        } else if (pos2 < 0) {
            addToStartPet(pet);
        } else {
            NodeDE temp = headDE;
            for (int i = 0; temp != null && i < pos2; i++) {
                temp = temp.getNext();
            }
            if (temp != null) {
                newNode.setNext(temp.getNext());
                newNode.setPrevi(temp);
                if (temp.getNext() != null) {
                    temp.getNext().setPrevi(newNode);
                }
                temp.setNext(newNode);
                size++;
            }
        }
    }



    // metodo para buscar una mascota por id
    public Pet getPetById(String id) {
        NodeDE temp = this.headDE;
        if (this.headDE != null) {
            while (temp!=null){
                if (temp.getData().getId().equals(id)){
                    return temp.getData();
                }
                temp=temp.getNext();
            }
        }
        return null;
    }
    // ejercicio 1 invertir lista
    public void invertListDe() throws ListDEException {
        if (this.headDE == null) {
            throw new ListDEException("La lista está vacía");
        }

        ListDE listDECp = new ListDE();
        NodeDE temp = this.headDE;
        while (temp != null) {
            listDECp.addToStartPet(temp.getData());
            temp = temp.getNext();
        }
        this.headDE = listDECp.getHeadDE();
    }


    //ejercicio 2 ordenar machos al comienzo
    public void orderMalesToStart()throws ListDEException  {
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
    public void intercalateMaleFamle() throws ListDEException {
        ListDE listDE1 = new ListDE();
        int sum = 0;
        NodeDE temp = this.headDE;
        if (this.headDE==null){
            System.out.println("No hay niños en la lista");
        }else {
            while (temp!=null){
                if (temp.getData().getGender()=='F'){
                    listDE1.addToStartPet(temp.getData());
                }
                temp=temp.getNext();
            }
            temp=this.headDE;
            while (temp!=null){
                if (temp.getData().getGender()=='M'){
                    listDE1.addInPosValidations(temp.getData(),sum);
                    temp=temp.getNext();
                    sum=sum+2;
                }else {
                    temp=temp.getNext();
                }
            }
            this.headDE=listDE1.getHeadDE();
        }
    }



    // ejercicios 4
    //elimina una mascota que tenga una edad determinda
    public void deletePetbyAge(byte age) throws ListDEException {
        if (this.headDE == null) {
            throw new ListDEException("no se puede borrar de la lista.");
        }
        NodeDE temp = this.headDE;
        ListDE listDECp = new ListDE();
        while (temp != null) {
            if (temp.getData().getAge() != age) {
                listDECp.addToStartPet(temp.getData());
            }
            temp = temp.getNext();
        }
        this.headDE = listDECp.getHeadDE();
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

    //ejercicio 6 reporte de mascotas por ciudad
    public int getCountPetBylocationCode(String code) {
        int count = 0;
        if (this.headDE != null) {
            NodeDE temp = this.headDE;
            while (temp != null) {
                if (temp.getData().getLocation().getCode().equals(code)) {
                    count++;
                }
                temp = temp.getNext();
            }
        }
        return count;
    }

    //ejercio 7
    //metodo para hacer que el costal de una mascota adelante posiciones con una posiciones dadas

    public int getPosPetById(String id)  {
        NodeDE temp = this.headDE;
        int acum = 1;
        if (this.headDE != null) {
            while (temp != null && temp.getData().getId().equals(id)) {
                acum = acum + 1;
                temp = temp.getNext();
            }
        }
        return acum;
    }



    public void gainPositionPet(String id, int gain)throws ListDEException {
        NodeDE temp = this.headDE;
        int sum=0;
        ListDE listDECp = new ListDE();
        if (this.headDE != null) {
            while (temp != null) {
                if (!temp.getData().getId().equals(id)) {
                    listDECp.addPet(temp.getData());
                    temp=temp.getNext();
                } else {
                    temp=temp.getNext();
                }
            }
        }
        if (gain!=1) {
            sum = gain - getPosPetById(id);
            listDECp.addInPosValidations(getPetById(id), sum);
        }else {
            listDECp.addToStartPet(getPetById(id));
        }
        this.headDE = listDECp.getHeadDE();
    }

    // ejercicio 8 metodo para hacer que una mascota pierda posiciones dadas
    public void losePositionPet(String id, int lose)throws ListDEException {
        NodeDE temp = this.headDE;
        int sum=0;
        ListDE listDECp = new ListDE();
        if (this.headDE != null) {
            while (temp != null) {
                if (!temp.getData().getId().equals(id)) {
                    listDECp.addPet(temp.getData());
                    temp=temp.getNext();
                } else {
                    temp=temp.getNext();
                }
            }
        }
        sum = getPosPetById(id)+lose;
        listDECp.addInPosValidations(getPetById(id), sum);
        this.headDE = listDECp.getHeadDE();
    }

    //ejercicio 9 obtener informe por edades
    public String reportByAge(){

        int quan1=0;
        int quan2=0;
        int quan3=0;
        int quan4=0;
        int quan5=0;
        NodeDE temp=this.headDE;
        if (this.headDE!=null){
            while (temp!=null) {
                if (temp.getData().getAge() >= 0 && temp.getData().getAge() <= 3){
                    quan1++;
                } else if (temp.getData().getAge() > 2 && temp.getData().getAge() <= 6) {
                    quan2++;
                }else if (temp.getData().getAge() > 6 && temp.getData().getAge() <= 9) {
                    quan3++;
                }else if (temp.getData().getAge() > 9 && temp.getData().getAge() <= 12) {
                    quan4++;
                }else if (temp.getData().getAge() > 12 && temp.getData().getAge() <= 15) {
                    quan5++;
                }
                temp=temp.getNext();
            }
        }
        return "mascotas entre 0 y 3 años: "+quan1+" mascotas entre 4 y 6 años: "+quan2+
                " mascotas entre 7 y 9 años: "+quan3+" mascotas entre 10 y 12 años: "+ quan4+
                " mascotas entre 13 y 15 años: "+quan5;
    }

    //ejercicio 10 permite enviar al final una mascota que comienze con una letra dada
    public void addToFinalPetNameChar(String letter) throws ListDEException {
        if (this.headDE != null) {
            ListDE listDECp = new ListDE();
            NodeDE temp = headDE;
            while (temp != null) {
                if (temp.getData().getName().startsWith(letter)) {
                    listDECp.addPet(temp.getData());
                } else {
                    listDECp.addToStartPet(temp.getData());
                }
                temp = temp.getNext();
            }

            this.headDE = listDECp.getHeadDE();
        }
    }




    // metodo para intercambiar extremos
    public void changeExtremes()
    {
        if(headDE!=null && headDE.getNext()!=null) {
            NodeDE temp = headDE;
            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            Pet copy = headDE.getData();
            headDE.setData(temp.getData());
            temp.setData(copy);
        }
    }



    //metodo para obtener la lista de ciudad y ademas se sabra cuantos hembras y machos hay por separado
    public void getReportPetsByLocationGendersByAge(byte age, ReportPetsLocationGenderDTO report){
        if(headDE !=null){
            NodeDE temp = this.headDE;
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

    /*
    pregunto a cabeza si tiene datos
        si el nodo a eliminar es el primero le digo a cabeza sea igual al nodo siguiente y despues le digo a la cabeza
        que el previous sea igual a nulo.
        si tiene datos, le digo a temporal que recorra la lista hasta estar parado en el nodo que tenga el mismo Id que
        voy a elminar, cuando este parado en este le digo al previous del siguiente que tome al anterior, y al nodo
        anterior donde estoy que su siguiente tome al siguiente de donde estoy parado.

        si el nodo del donde estoy parado es el ultimo le digo al previous de este sea igual a nulo


     */
    public void deletePetByIdInNode(String id) {
        if (this.headDE != null) {

            if (this.headDE.getData().getId().equals(id)) {
                this.headDE=headDE.getNext();
                if(this.headDE !=null) {
                    this.headDE.setPrevi(null);
                }else {
                    this.headDE=null;
                }
            } else {
                NodeDE temp = this.headDE;
                while (temp!=null){
                    if (temp.getData().getId().equals(id)) {
                        temp.getPrevi().setNext(temp.getNext());
                        if (temp.getNext()!=null){
                            temp.getNext().setPrevi(temp.getPrevi());
                        }
                    }
                    temp=temp.getNext();
                }
            }
        }
    }

    public int getLength() {
        int total = 0;
        NodeDE temp = headDE;
        while (temp != null) {
            total++;
            temp = temp.getNext();
        }
        return total;
    }

    public void addInPosValidations(Pet pet, int pos2) throws ListDEException {
        NodeDE temp = headDE;
        NodeDE newNode = new NodeDE(pet);
        int listLength = getLength();
        if (pos2 < 0 || pos2 >= listLength)
            addPet(pet);
        if (pos2 == 0) {
            newNode.setNext(headDE);
            this.headDE.setPrevi(newNode);
            headDE = newNode;


        } else {
            for (int i = 0; temp.getNext() != null && i < pos2 - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            newNode.setPrevi(temp);
            temp.setNext(newNode);
            temp.getNext().setPrevi(newNode);
        }
    }



}
