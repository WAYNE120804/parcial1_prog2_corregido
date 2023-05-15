package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.ListDE;
import co.edu.umanizales.tads.model.ListDECircle;
import co.edu.umanizales.tads.model.NodeDE;
import co.edu.umanizales.tads.model.Pet;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Data
@Service
public class ListDECircleService {
    private ListDECircle pets;

    public  ListDECircleService(){
        pets=new ListDECircle();
    }

    public NodeDE getPets(){return pets.getHeadDEC();}

    public ArrayList<Pet> printList(){return pets.printList();}

    //adcionar
    public void addPet(Pet pet) throws ListDEException {pets.addPet(pet);}
}
