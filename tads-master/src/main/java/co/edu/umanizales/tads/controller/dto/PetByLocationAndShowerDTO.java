package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetByLocationAndShowerDTO {
    private Location location;
    private int quantitygender;
    private int quantityshower;
    private int quantitynoshower;
}

