package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;

@Data
public class PetDTO {
    private String type;
    private String name;
    private char gender;
    private String id;
    private byte age;
    private String ownercontact;
    private String codeLocation;

}
