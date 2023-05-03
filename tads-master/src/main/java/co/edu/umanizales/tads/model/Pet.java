package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pet {
    private String type;
    private String name;
    private char gender;
    private String id;
    private Location location;
    private byte age;
    private String ownercontact;

}
