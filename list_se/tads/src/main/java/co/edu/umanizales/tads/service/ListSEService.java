package co.edu.umanizales.tads.service;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.ListSE;
import co.edu.umanizales.tads.model.Node;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ListSEService {
    private ListSE kids;

    public ListSEService(){
        kids=new ListSE();
        kids.add(new Kid("123","Carlos",(byte) 4,"m"));
        kids.add(new Kid("456","Mariana",(byte) 3,"f"));
        kids.add(new Kid("789","Santiago",(byte) 7,"m"));
        kids.add(new Kid("106","Pedro",(byte) 9,"m"));
        kids.add(new Kid("473","Estefania",(byte) 8,"f"));
        kids.add(new Kid("826","Carla",(byte) 10,"f"));
        kids.add(new Kid("724","Tomas",(byte) 3,"m"));

    }
/*
    public Node getKids() {
        return kids.getHead();
    }

    public void createKid(Kid kid) {
        this.kids.add(kid);
    }

 */

    public void deleteKidbyAge(byte age){kids.deleteKidbyAge( age);}
    public void gainPositionKid(String id, int gain){kids.gainPositionKid(id,gain);}

    public void kidStart(String gender){kids.kidStart(gender);}
    }


