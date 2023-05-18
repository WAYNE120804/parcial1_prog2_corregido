package co.edu.umanizales.tads.service;


import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListSEService {
    private ListSE kids;

    public ListSEService() {
        kids = new ListSE();
    }

    // ejercicio 1 invertir lista
   public void invert()throws ListSEException{kids.invert();}
    public Node getKids(){return kids.getHead();}

    // adicionar
    public void add(Kid kid)throws ListSEException{kids.add (kid);}

    //ejercicio 2 ordenar niños al comienzo
    public void orderBoysToStart()throws ListSEException {kids.orderBoysToStart ();}

    //ejercicio 3 intercalar niño niña
    public void intercalateBoyGirl()throws ListSEException{kids.intercalateBoyGirl();}

    // ejercicios 4
    //elimina un niño que tenga una edad determinda
    public void deleteKidbyAge(byte age)throws ListSEException{kids.deleteKidbyAge( age);}

    //ejercicio 5 promedio de edades
    public float averageAge()throws ListSEException{return kids.averageAge();}

    //ejercicio 6 reporte de niños por ciudad
    public int getCountKidsBylocationsCode(String code){return  kids.getCountKidsBylocationCode(code);}

    //ejercio 7
    //metodo para hacer que el costal de un niño adelante posiciones con una posiciones dadas
    public void gainPositionKid(String id, int gain)throws ListSEException{kids.gainPositionKid(id,gain);}

    // ejercicio 8 metodo para hacer que un niño pierda posiciones dadas
    public void losePositionKid(String id, int lose)throws ListSEException{kids.losePositionKid(id, lose);}

    //ejercicio 9 metodo para obtener un reporte por rango de edades
    public String reportByAge(){return kids.reportByAge();}

    //ejercicio 10
    public void addToFinalNameChar(String letter)throws ListSEException {kids.addToFinalNameChar(letter);}

    //metodo para intecambiar extremos
    public void changeExtremes(){kids.changeExtremes();}

    //metodo para obtener la lista de ciudad y ademas se sabra cuantos niños y niñas hay por separado
    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){kids.
            getReportKidsByLocationGendersByAge(age, report);}







}
