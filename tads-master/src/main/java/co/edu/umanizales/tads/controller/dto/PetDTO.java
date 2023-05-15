package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PetDTO {
    @Pattern(regexp = "^[a-zA-Z]*$",message = "el formato del tipo de animal no es valido")
    private String type;
    @Pattern(regexp = "^[a-zA-Z]*$",message = "el formato del nombre no es valido")
    private String name;
    @NotNull
    @Size(max = 1,message = "el genero proporcionado no es valido, proporcione M para masculino o F para femenino")
    private char gender;
    @Digits(integer = 10,fraction = 0,message = "se necesita un identicador para el niño")
    private String id;
    @Digits(integer = 2,fraction = 0,message = "la edad proporcionada no es valida")
    @Size(max = 2)
    private byte age;
    @Digits(integer = 10,fraction = 0,message = "el numero proporcionado no es valida")
    private String ownercontact;

    private Boolean shower;
    @Size(min = 3, max = 8, message = "codigo invalido")
    private String codeLocation;



}
