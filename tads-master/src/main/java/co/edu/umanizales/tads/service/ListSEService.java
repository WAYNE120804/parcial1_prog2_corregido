package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
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

   public void invert(){
        kids.invert();
    }
    public Node getKids(){return kids.getHead();}
    public void changeExtremes(){kids.changeExtremes();}
    public void add(Kid kid){kids.add(kid);}

    public int getCountKidsBylocationsCode(String code){return  kids.getCountKidsBylocationCode(code);}
    public void deleteKidbyAge(byte age){kids.deleteKidbyAge( age);}
    public void gainPositionKid(String id, int gain){kids.gainPositionKid(id,gain);}
    public void losePositionKid(String id, int lose){kids.losePositionKid(id, lose);}
    public void orderBoysToStart(){kids.orderBoysToStart();}
    public void getReportKidsByLocationGendersByAge(byte age, ReportKidsLocationGenderDTO report){kids.
            getReportKidsByLocationGendersByAge(age, report);}




}
