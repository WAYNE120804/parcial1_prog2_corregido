package co.edu.umanizales.tads.model;

import ch.qos.logback.core.joran.spi.ElementSelector;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
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
    public void add(Kid kid) throws ListSEException {
        if (kid == null) {
            throw new ListSEException("no se puede agregar un niño sin datos a la lista");
        }
        if (head != null) {
            Node temp = head;
            while (temp.getNext() != null) {
                if (temp.getData().getIdentification() == null ||
                        temp.getData().getIdentification().equals(kid.getIdentification())) {
                    throw new ListSEException("ya existe un niño");
                }
                temp = temp.getNext();
            }
            if (temp.getData().getIdentification().equals(kid.getIdentification())) {
                throw new ListSEException("ya existe un niño");
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
    public void addToStart(Kid kid) throws ListSEException {
        if (kid == null) {
            throw new ListSEException("no se puede agregar un niño sin datos");
        }
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
    public void invert() throws ListSEException {
        if (this.head != null) {
            ListSE listCp = new ListSE();
            Node temp = this.head;
            while (temp != null) {
                listCp.addToStart(temp.getData());
                temp = temp.getNext();
            }
            this.head = listCp.getHead();
        } else {
            throw new ListSEException("no hay datos en la lista para invertir");
        }
    }

    //ejercicio 2 ordenar niños al comienzo
    public void orderBoysToStart() throws ListSEException {
        if (this.head == null) {
            throw new ListSEException("la lista esta vacia");
        } else {
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
    /*
    public void intercalateBoyGirl() throws ListSEException {
        if (this.head == null) {
            throw new ListSEException("La lista está vacía.");
        }
        Node temp = this.head;
        ListSE ListSEC = new ListSE();
        while (temp != null) {
            if (temp.getData().getGender() == 'M') {
                ListSEC.add(temp.getData());
            }
            temp = temp.getNext();
        }
        temp = this.head;
        int sum=0;
        while (temp != null) {
            if (temp.getData().getGender() == 'F') {
                ListSEC.addInpos(temp.getData(), sum);
                sum=sum+2;
            }
            temp = temp.getNext();
        }

        this.head = ListSEC.getHead();
    }

     */
    public void intercalateBoyGirl() throws ListSEException {
        ListSE listSE1 = new ListSE();
        int sum = 0;
        Node temp = this.head;
        if (this.head==null){
            System.out.println("No hay niños en la lista");
        }else {
            while (temp!=null){
                if (temp.getData().getGender()=='F'){
                    listSE1.addToStart(temp.getData());
                }
                temp=temp.getNext();
            }
            temp=this.head;
            while (temp!=null){
                if (temp.getData().getGender()=='M'){
                    listSE1.addInpos(temp.getData(),sum);
                    temp=temp.getNext();
                    sum=sum+2;
                }else {
                    temp=temp.getNext();
                }
            }
            this.head=listSE1.getHead();
        }
    }
    // ejercicios 4
    //elimina un niño que tenga una edad determinda
    public void deleteKidbyAge(byte age) throws ListSEException {
        if (this.head == null) {
            throw new ListSEException("La lista está vacía.");
        }

        Node temp = this.head;
        ListSE listSECp = new ListSE();
        while (temp != null) {
            if (temp.getData().getAge() != age) {
                listSECp.addToStart(temp.getData());
            }
            temp = temp.getNext();
        }
        this.head = listSECp.getHead();
    }


    //ejercicio 5 promedio de edades
    public float averageAge() throws ListSEException {
        float count = 0;
        float ages = 0;
        if (this.head != null) {
            Node temp = this.head;
            while (temp.getNext() != null) {
                count++;
                ages = ages + temp.getData().getAge();
                temp = temp.getNext();
            }
            return (float) ages / count;
        } else {
            throw new ListSEException("La lista está vacía");
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
        int acum = 1;
        if (this.head != null) {
            while (temp != null && temp.getData().getIdentification().equals(id)) {
                    acum = acum + 1;
                    temp = temp.getNext();
            }
        }
        return acum;
    }


    public void gainPositionKid(String id, int gain) throws ListSEException {
        Node temp = this.head;
        int sum=1;
        ListSE listSECp = new ListSE();
        if (head != null) {
            while (temp != null) {
                if (!temp.getData().getIdentification().equals(id)) {
                    listSECp.add(temp.getData());
                    temp=temp.getNext();
                } else {
                    temp=temp.getNext();
                }
            }
        }
        if (gain!=1) {
            sum = gain - getPosById(id);
            listSECp.addInPosValidations(getKidById(id), sum);
        }else {
            listSECp.addToStart(getKidById(id));
        }
        this.head = listSECp.getHead();
    }

    // ejercicio 8 metodo para hacer que un niño pierda posiciones dadas

    public void losePositionKid(String id, int lose) throws ListSEException {
        Node temp = this.head;
        int sum=1;
        ListSE listSECp = new ListSE();
        if (head != null) {
            while (temp != null) {
                if (!temp.getData().getIdentification().equals(id)) {
                    listSECp.add(temp.getData());
                    temp=temp.getNext();
                } else {
                    temp=temp.getNext();
                }
            }
        }
        sum = lose + getPosById(id);
        listSECp.addInPosValidations(getKidById(id), sum);
        this.head = listSECp.getHead();
    }

    public void addInPosValidations(Kid kid, int pos2) throws ListSEException {
        Node temp = head;
        Node newNode = new Node(kid);
        int listLength = getLength();
        if (pos2 < 0 || pos2 >= listLength)
            add(kid);
        if (pos2 == 0) {
            newNode.setNext(head);
            head = newNode;

        } else {
            for (int i = 0; temp.getNext() != null && i < pos2 - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            temp.setNext(newNode);
        }
    }

    //ejercicio 9 obtener informe por edades
    public String reportByAge() {

        int quan1 = 0;
        int quan2 = 0;
        int quan3 = 0;
        int quan4 = 0;
        int quan5 = 0;
        Node temp = this.head;
        if (this.head != null) {
            while (temp != null) {
                if (temp.getData().getAge() >= 0 && temp.getData().getAge() <= 3) {
                    quan1++;
                } else if (temp.getData().getAge() > 2 && temp.getData().getAge() <= 6) {
                    quan2++;
                } else if (temp.getData().getAge() > 6 && temp.getData().getAge() <= 9) {
                    quan3++;
                } else if (temp.getData().getAge() > 9 && temp.getData().getAge() <= 12) {
                    quan4++;
                } else if (temp.getData().getAge() > 12 && temp.getData().getAge() <= 15) {
                    quan5++;
                }
                temp = temp.getNext();
            }
        }
        return "niños entre 0 y 3 años: " + quan1 + ", " + " niños entre 4 y 6 años: " + quan2 +
                ", " + " niños entre 7 y 9 años: " + ", " + quan3 + ", " + " niños entre 10 y 12 años: " + ", " + quan4 +
                ", " + " niños entre 13 y 15 años: " + ", " + quan5;
    }


    //ejercicio 10 permite enviar al final un niño que comienze con una letra dada
    public void addToFinalNameChar(String letter) throws ListSEException {
        ListSE listSECp = new ListSE();
        Node temp = head;
        if (this.head != null) {
            while (temp != null) {
                if (temp.getData().getName().startsWith(letter)) {
                    listSECp.add(temp.getData());
                } else {
                    listSECp.addToStart(temp.getData());
                }
                temp = temp.getNext();
            }
        }
        this.head = listSECp.getHead();
    }


    // metodo para buscar niño por id
    public Kid getKidById(String id) {
        Node temp = this.head;
        if (head != null) {
            while (temp != null) {
                if (temp.getData().getIdentification().equals(id)) {
                    return temp.getData();
                }
                temp = temp.getNext();
            }
        }
        return null;
    }


    public int getLength(){
        int total=0;
        Node temp=head;
        while (temp!=null){
            total++;
            temp=temp.getNext();
        }
        return total;
}

    //metodo para añadir nuevo nodo y nuevo niño en un posicion
    public void addInpos(Kid kid, int pos) throws ListSEException {
        Node temp = this.head;
        Node newNode = new Node(kid);
        int listLength=getLength();
        if (pos<0||pos>= listLength) {
            add(kid);
            if (pos ==0) {
                newNode.setNext(head);
                head=newNode;
            } else {
                for (int i = 0;i < pos-1&& temp.getNext() != null  ; i++) {
                    temp = temp.getNext();
                }
                newNode.setNext(temp.getNext());
                temp.setNext(newNode);
            }
        }
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
