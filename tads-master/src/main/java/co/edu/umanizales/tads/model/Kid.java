package co.edu.umanizales.tads.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class Kid {
    @Digits(integer = 10,fraction = 0,message = "se necesita un identicador para el niño")
    private String identification;
    @Pattern(regexp = "^[a-zA-Z]*$",message = "el formato del nombre no es valido")
    private String name;

    @Digits(integer = 2,fraction = 0,message = "la edad proporcionada no es valida")
    @Size(max = 2)
    private byte age;

    @NotNull
    @Size(max = 1,message = "el genero proporcionado no es valido, proporcione M para masculino o F para femenino")
    private char gender;

    @NotBlank
    @Size(min = 3, max = 8, message = "codigo invalido")
    private Location location;
}
