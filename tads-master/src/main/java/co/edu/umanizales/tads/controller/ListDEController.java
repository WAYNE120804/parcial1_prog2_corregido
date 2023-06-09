package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListDEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.model.Pet;
import co.edu.umanizales.tads.service.ListDEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/listde")
public class ListDEController {

    @Autowired
    private ListDEService listDEService;

    @Autowired
    private LocationService locationService;

    @GetMapping(path ="/getlistde")
    public ResponseEntity<ResponseDTO> getPets(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listDEService.printList(),null), HttpStatus.OK);
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
            listDEService.addPet(new Pet(petDTO.getType(), petDTO.getName(), petDTO.getGender(), petDTO.getId(),
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
    @PostMapping(path = "/addinposition/{pos2}")
    public ResponseEntity<ResponseDTO> addPetInPosition(@RequestBody @Valid PetDTO petDTO, @PathVariable int pos2) {
        try {
            Location location = locationService.getLocationByCode(petDTO.getCodeLocation());
            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe", null), HttpStatus.OK);
            }
            listDEService.addInPosValidations(new Pet(petDTO.getType(), petDTO.getName(), petDTO.getGender(),
                    petDTO.getId(), petDTO.getAge(), petDTO.getOwnercontact(), petDTO.getShower(), location), pos2);

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



    //ejercicio 1 invertir lista
    @GetMapping(path ="/invertlistde")
    public ResponseEntity<ResponseDTO> invertListDe(){
        try{
            listDEService.invertListDe();
        }catch (ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"la lista se ha invertido",null), HttpStatus.OK);
    }

    //ejercicio 2 ordenar machos al comienzo
    @GetMapping(path ="/ordermalestostart")
    public ResponseEntity<ResponseDTO> orderMalesToStart(){
        try {
            listDEService.orderMalesToStart();
        }catch (ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200,"los machos quedaron al comienzo",null), HttpStatus.OK);
    }

    //ejercicio 3 intercalar macho hembra
    @GetMapping(path ="/intercalatemalefamle")
    public ResponseEntity<ResponseDTO> intercalateMaleFamle()throws ListDEException{
        listDEService.intercalateMaleFamle();
        return new ResponseEntity<>(new ResponseDTO(
                200,"mascotas intercaladas",null), HttpStatus.OK);
    }

    //ejercicio 4  eleminiar una mascota que tenga una edad determinada
    @GetMapping(path ="/deletepet/{age}")
    public ResponseEntity<ResponseDTO> deletePetByAge(@PathVariable byte age){
        try {
            listDEService.deletePetByAge(age);
        }catch (ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200,"mascota eliminada",null), HttpStatus.OK);
    }

    //ejercicio 5 promedio de edades
    @GetMapping(path ="/averageage")
    public ResponseEntity<ResponseDTO> averageAge(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listDEService.averageAge(),null), HttpStatus.OK);
    }

    //ejercico 6 ubicaciones___________________________________________________________________________________________

    //obtener mascotas por ubicaciones (pais, departamentos, ciudades)
    @GetMapping(path = "/petsbylocations")
    public ResponseEntity<ResponseDTO> getPetByLocations(){
        List<PetByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listDEService.getCountPetBylocationCode(loc.getCode());
            if(count>0){
                petsByLocationDTOList.add(new PetByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,petsByLocationDTOList,
                null), HttpStatus.OK);
    }

    //obtener niños por departamento
    @GetMapping(path = "/petsbydepartament")
    public ResponseEntity<ResponseDTO> getPetsByDepartament(){
        List<PetByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(5)){
            int count = listDEService.getCountPetBylocationCode(loc.getCode());

            if(count>0){
                petsByLocationDTOList.add(new PetByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,petsByLocationDTOList,
                null), HttpStatus.OK);
    }

    //niños por ciudad
    @GetMapping(path = "/petsbycity")
    public ResponseEntity<ResponseDTO> getPetsByCity(){
        List<PetByLocationDTO> petsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(8)){
            int count = listDEService.getCountPetBylocationCode(loc.getCode());

            if(count>0){
                petsByLocationDTOList.add(new PetByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,petsByLocationDTOList,
                null), HttpStatus.OK);
    }

    //______________________________________________________________________________________________________________

    //ejercicio 7 ganar posicion de mascota
    @GetMapping(path = "/gainpositionpet")
    public ResponseEntity<ResponseDTO>gainPositionById(@RequestBody Map<String, Object> requestBody)
            throws ListDEException{
            String id=(String) requestBody.get("id");
            Integer gain=(Integer)requestBody.get("gain");
            listDEService.gainPositionPet(id, gain);
            return new ResponseEntity<>(new ResponseDTO(
                    200,"la mascota gano posicion",null), HttpStatus.OK);

    }

    // ejercicio 8 perder posicion de la mascota
    @GetMapping(path = "/losepositionpet")
    public ResponseEntity<ResponseDTO>losePositionById(@RequestBody Map<String, Object> requestBody)
            throws ListDEException{
            String id=(String) requestBody.get("id");
            Integer lose=(Integer)requestBody.get("lose");
            listDEService.losePositionPet(id, lose);
        return new ResponseEntity<>(new ResponseDTO(
                200,"la mascota perdio posicion",null), HttpStatus.OK);
    }

    //ejercicio 9
    @GetMapping(path = "/reportbyage")
    public ResponseEntity<ResponseDTO>reportByAge(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listDEService.reportByAge(),null), HttpStatus.OK);
    }

    //ejercicio 10 enviar niño al final si comienza su nombre por una letra dada
    @GetMapping(path = "/addToFinalNameChar/{letter}")
    public ResponseEntity<ResponseDTO>addToFinalNameChar(@PathVariable String letter){
        try {
            listDEService.addToFinalPetNameChar(letter);
        }catch (ListDEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200,"la mascota ahora esta al final de la lista",null), HttpStatus.OK);
    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() throws ListDEException{
        listDEService.changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha intercambiado los extremos",
                null), HttpStatus.OK);
    }

    //obtener una informe de niños por cada ciudad con su respetivo genero
    @GetMapping(path = "/petsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportPetsLocationGenders(@PathVariable byte age) {
        ReportPetsLocationGenderDTO report = new ReportPetsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listDEService.getReportPetsByLocationGendersByAge(age,report);
        return new ResponseEntity<>(new ResponseDTO(200,report, null), HttpStatus.OK);
    }

    //obtener una informe de niños por cada ciudad con su respetivo genero
    @GetMapping(path = "/deletepetbyidinnode/{id}")
    public ResponseEntity<ResponseDTO> deletePetByIdInNode(@PathVariable String id) {
        listDEService.deletePetByIdInNode(id);
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha eliminado la mascota",
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletepetbyid/{id}")
    public ResponseEntity<ResponseDTO> deletePetById(@PathVariable String id)throws ListDEException {
        listDEService.deletePet(id);
        return new ResponseEntity<>(new ResponseDTO(200,"Se ha eliminado la mascota",
                null), HttpStatus.OK);
    }


}