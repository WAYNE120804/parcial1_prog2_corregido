package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.controller.dto.ReportPetsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.ListDE;
import co.edu.umanizales.tads.model.NodeDE;
import co.edu.umanizales.tads.model.Pet;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ListDEService {
    private ListDE pets;

    public  ListDEService(){
        pets=new ListDE();
    }

    public NodeDE getPets(){return pets.getHeadDE();}

    public String putToString(){return pets.toString();}

    //adcionar
    public void addPet(Pet pet) throws ListDEException {pets.addPet(pet);}

    //ejercicio 1 invertir lista
    public void invertListDe()throws ListDEException{pets.invertListDe();}

    //ejercicio 2 ordenar machos al comiezo
    public void orderMalesToStart()throws ListDEException{pets.orderMalesToStart();}

    //ejercicio 3 intercalar macho hembra


    //ejercicio 4 eleminiar una mascota que tenga una edad determinada
    public void deletePetByAge(byte age)throws ListDEException{pets.deletePetbyAge(age);}

    //ejercicio 5 promedio de edades
    public float averageAge(){return pets.averageAge();}

    //ejercicio 6 reporte de mascota por ciudad
    public int getCountPetBylocationCode(String code){return pets.getCountPetBylocationCode(code);}

    //ejercicio 7 hacer que el costal de una mascota adelante posiciones con una posiciones dadas
    public void gainPositionPet(String id, int gain)throws ListDEException{pets.gainPositionPet(id, gain);}

    // ejercicio 8 metodo para hacer que una mascota pierda posiciones dadas
    public void losePositionPet(String id, int lose)throws ListDEException{pets.losePositionPet(id,lose);}

    //ejercicio 9 obtener informe por edades
    public String reportByAge(){return pets.reportByAge();}

    //ejercicio 10 permite enviar al final una mascota que comienze con una letra dada
    public void addToFinalPetNameChar(String letter)throws ListDEException {pets.addToFinalPetNameChar(letter);}

    // metodo para intercambiar extremos
    public void changeExtremes() {pets.changeExtremes();}

    //metodo para obtener la lista de ciudad y ademas se sabra cuantos hembras y machos hay por separado
    public void getReportPetsByLocationGendersByAge(byte age, ReportPetsLocationGenderDTO report){
        pets.getReportPetsByLocationGendersByAge(age,report);}

    public void deletePetByIdInNode(String id){pets.deletePetByIdInNode(id);}
}
