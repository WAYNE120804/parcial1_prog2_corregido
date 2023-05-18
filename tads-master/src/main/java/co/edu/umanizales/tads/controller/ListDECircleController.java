package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ErrorDTO;
import co.edu.umanizales.tads.controller.dto.PetDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDECircleService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listdecircle")
public class ListDECircleController {

    @Autowired
    private ListDECircleService listDECircleService;

    @Autowired
    private LocationService locationService;

    @GetMapping(path ="/getlistdecircle")
    public ResponseEntity<ResponseDTO> getPets(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listDECircleService.printList(),null), HttpStatus.OK);
    }

    //adicionar mascota
    @PostMapping
    public ResponseEntity<ResponseDTO> addPet(@RequestBody @Valid PetDTO petDTO) {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe", null), HttpStatus.OK);
            }
            listDECircleService.addPet(new Pet(petDTO.getType(), petDTO.getName(), petDTO.getGender(), petDTO.getId(),
                    petDTO.getAge(), petDTO.getOwnercontact(), petDTO.getShower(), location));

            return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado la mascota", null),
                    HttpStatus.OK);
        } catch (ListDEException e) {
            List<ErrorDTO> errorDTOS = new ArrayList<>();
            ErrorDTO errorDTO = new ErrorDTO(Integer.parseInt("400"), "Debe poner un ID distinto");
            errorDTOS.add(errorDTO);
            return new ResponseEntity<>(new ResponseDTO(400, "Ya existe una mascota con esa identificacion",
                    errorDTOS), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            List<ErrorDTO> errorDTOS = new ArrayList<>();
            ErrorDTO errorDTO = new ErrorDTO(Integer.parseInt("400"), e.getMessage());
            errorDTOS.add(errorDTO);
            return new ResponseEntity<>(new ResponseDTO(400, "Error en los datos enviados",
                    errorDTOS), HttpStatus.OK);
        }
    }

    @GetMapping(path = "/addtostartpet")
    public ResponseEntity<ResponseDTO> addToStartPet(@RequestBody @Valid PetDTO petDTO) {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe", null), HttpStatus.OK);
            }
            listDECircleService.addToStartPet(new Pet(petDTO.getType(), petDTO.getName(), petDTO.getGender(),
                    petDTO.getId(), petDTO.getAge(), petDTO.getOwnercontact(), petDTO.getShower(), location));

            return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado la mascota", null),
                    HttpStatus.OK);
    }

    @GetMapping(path = "/showerrandomlypet")
    public ResponseEntity<ResponseDTO> showerRandomlyPet() throws ListDEException {
        listDECircleService.showerRandomlyPet();
        return new ResponseEntity<>(new ResponseDTO(
                200,"la mascota se ha bañado",null), HttpStatus.OK);
    }

}
