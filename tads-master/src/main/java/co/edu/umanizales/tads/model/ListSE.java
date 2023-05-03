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
    public void add(Kid kid)  {
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                  temp = temp.getNext();
            }
            /// Parado en el último
            Node newNode = new Node(kid);
            temp.setNext(newNode);

        }
        else {
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

    // ejercicio 1 invertir lista
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

    //ejercicio 2 ordenar niños al comienzo
    public void orderBoysToStart()  {
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

    //ejercicio 3 intercalar niño niña

    public void intercalateBoyGirl(){
        Node temp=head;
        ListSE ListSECp=new ListSE();
        if(head!=null) {
            while (temp != null){
                if (temp.getData().getGender() == 'M') {
                    if (temp.getNext().getData().getGender() == 'F') {
                        ListSECp.add((temp.getData()));
                    } else if (temp.getNext().getData().getGender() == 'M') {
                        temp= temp.getNext();
                    }
                } else if (temp.getData().getGender() == 'F') {
                    if (temp.getNext().getData().getGender() == 'M') {
                        ListSECp.add((temp.getData()));
                    } else if (temp.getNext().getData().getGender() == 'F') {
                        temp=temp.getNext();
                    }
                    temp=temp.getNext();
                }
            }
            this.head = ListSECp.getHead();
        }
    }

    // ejercicios 4
    //elimina un niño que tenga una edad determinda
    public void deleteKidbyAge(byte age) {
        Node temp = this.head;
        ListSE listSECp = new ListSE();
        if (this.head != null) {
            while (temp != null) {
                if (temp.getData().getAge() != age) {
                    listSECp.addToStart(temp.getData());
                }
                temp = temp.getNext();
            }
            this.head = listSECp.getHead();
        }
    }

    //ejercicio 5 promedio de edades
    public float averageAge(){
        float count=0;
        float ages=0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp.getNext()!=null){
                count++;
                ages=ages+temp.getData().getAge();
                temp=temp.getNext();
            }
            return (float) ages/count;
        }else {
            return (int)0;
        }
    }


    //ejercicio 6 reporte de niños por ciudad
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

    //ejercio 7
    //metodo para hacer que el costal de un niño adelante posiciones con una posiciones dadas

    public int getPosById(String id) {
        Node temp = this.head;
        int acum = 0;
        if (this.head != null) {
            while (temp != null) {
                while (!temp.getData().getIdentification().equals(id)) {
                    acum = acum + 1;
                    temp = temp.getNext();
                    return acum;
                }
            }
        }
        return acum;
    }


    public void gainPositionKid(String id, int gain) {
        Node temp = head;
        gain = 0;
        int sum = 0;
        ListSE listSECp = new ListSE();
        if (head != null) {
            while (temp != null) {
                if (!temp.getData().getIdentification().equals(id)) {
                    listSECp.add(temp.getData());
                    temp = temp.getNext();
                }else {
                    temp=temp.getNext();

                }
            }
        }
        sum = getPosById(id)-gain;
        listSECp.addInpos(getKidById(id), sum);
        this.head=listSECp.getHead();
    }

    // ejercicio 8 metodo para hacer que un niño pierda posiciones dadas

    public void losePositionKid(String id, int lose) {
        Node temp = head;
        lose = 0;
        int sum = 0;
        ListSE listSECp = new ListSE();
        if (head != null) {
            while (temp != null) {
                if(!temp.getData().getIdentification().equals(id)) {
                    listSECp.add(temp.getData());
                    temp.getNext();
                }else {
                    temp.getNext();
                }
            }
        }
        sum = lose + getPosById(id);
        listSECp.addInpos(getKidById(id), sum);
        this.head=listSECp.getHead();
    }

    //ejercicio 9 obtener informe por edades
    public String reportByAge(){
        
        int quan1=0;
        int quan2=0;
        int quan3=0;
        int quan4=0;
        int quan5=0;
        Node temp=this.head;
        if (this.head!=null){
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
        return "niños entre 0 y 3 años: "+quan1+" niños entre 4 y 6 años: "+quan2+
                " niños entre 7 y 9 años: "+quan3+" niños entre 10 y 12 años: "+ quan4+
                " niños entre 13 y 15 años: "+quan5;
    }


    //ejercicio 10 permite enviar al final un niño que comienze con una letra dada
    public void addToFinalNameChar(String letter)  {
        ListSE listSECp=new ListSE();
        Node temp=head;
        if (this.head!=null){
            while (temp!=null) {
                if (temp.getData().getName().startsWith(letter) != temp.getData().getName().startsWith(letter)) {
                    listSECp.addToStart(temp.getData());
                } else {
                    listSECp.add(temp.getData());
                }
                temp = temp.getNext();
            }
        }
        this.head=listSECp.getHead();
}


// metodo para buscar niño por id
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

// metodo para intercambiar extremos
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

    //metodo para obtener la lista de ciudad y ademas se sabra cuantos niños y niñas hay por separado
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
