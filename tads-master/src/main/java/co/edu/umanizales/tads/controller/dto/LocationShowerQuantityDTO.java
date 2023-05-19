package co.edu.umanizales.tads.controller.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class LocationShowerQuantityDTO {
    private String city;

    private List<ShowerQuantityDTO> showers;

    private int total;

    public LocationShowerQuantityDTO(){
        this.city=city;
        this.total=0;
        this.showers=new ArrayList<>();
        this.showers.add(new ShowerQuantityDTO(false,0));
        this.showers.add(new ShowerQuantityDTO(true,0));

    }
}
