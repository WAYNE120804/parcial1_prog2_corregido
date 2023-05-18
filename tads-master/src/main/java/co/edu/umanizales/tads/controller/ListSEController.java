package co.edu.umanizales.tads.controller;


import co.edu.umanizales.tads.controller.dto.*;
import co.edu.umanizales.tads.exception.ListSEException;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;

    @Autowired
    private Validator validator;




    //adicionar niños
    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody @Valid KidDTO kidDTO) {
        try {
            Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());

            if (location == null) {
                return new ResponseEntity<>(new ResponseDTO(
                        404, "La ubicación no existe", null), HttpStatus.OK);
            }

            if (kidDTO.getAge() <= 0) {
                throw new IllegalArgumentException("La edad del niño debe ser mayor a cero");
            }

            if (kidDTO.getName().isEmpty()) {
                throw new IllegalArgumentException("El nombre del niño no puede estar vacío");
            }

            listSEService.add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(),
                    kidDTO.getGender(), location));

            return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado el petacón", null),
                    HttpStatus.OK);

        } catch (ListSEException e) {
            // Manejo de excepción ListSEException
        } catch (IllegalArgumentException e) {
            List<ErrorDTO> errorDTOS = new ArrayList<>();
            ErrorDTO errorDTO = new ErrorDTO(Integer.parseInt("400"), e.getMessage());
            errorDTOS.add(errorDTO);
            return new ResponseEntity<>(new ResponseDTO(400, "Error en la solicitud", errorDTOS),
                    HttpStatus.OK);
        }

        List<ErrorDTO> errorDTOS = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO(Integer.parseInt("400"), "Debe poner una identificación distinta");
        errorDTOS.add(errorDTO);
        return new ResponseEntity<>(new ResponseDTO(400, "Ya existe un niño con esa identificación",
                errorDTOS), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids(),null), HttpStatus.OK);
    }


    //ejercicio 1 invertir lista
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        try {
            listSEService.invert();
        }catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(200,e.getMessage(), null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(200,"SE ha invertido la lista",
                null), HttpStatus.OK);

    }

    //ejercicio 2 niños al comienzo
    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO>orderBoysToStart(){
        try {
            listSEService.orderBoysToStart();
        }catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ResponseDTO(
                200,"niños ordenados al comienzo",null), HttpStatus.OK);
    }

    //ejercicio 3 intercalar niño niña
    @GetMapping(path = "/intercaleboyandgirl")
    public ResponseEntity<ResponseDTO>intercalateBoyGirl(){
        try {
            listSEService.intercalateBoyGirl();
            return new ResponseEntity<>(new ResponseDTO(
                    200,"los niños fueron intercalados",null), HttpStatus.OK);
        }catch(ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);
        }
    }

    // ejercicio 4 borrar niño por edad
    @GetMapping(path = "/deletekid/{age}")
    public ResponseEntity<ResponseDTO>deleteKidByAge(@PathVariable byte age){
        try {
            listSEService.deleteKidbyAge(age);
        }catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,"niño eliminado",null), HttpStatus.OK);
    }

    //ejercicio 5 promedio de edades
    @GetMapping(path = "/averageage")
    public ResponseEntity<ResponseDTO>averageAge(){
        try {
            return new ResponseEntity<>(new ResponseDTO(
                    200,(float)listSEService.averageAge(),null), HttpStatus.OK);
        }catch (ListSEException e){
        return new ResponseEntity<>(new ResponseDTO(
                200,e.getMessage(),null), HttpStatus.OK);
        }
    }

    //ejercico 6 ubicaciones___________________________________________________________________________________________

    //obtener niños por ubicaciones (pais, departamentos, ciudades)
    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsByCity(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    //obtener niños por departamento
    @GetMapping(path = "/kidsbydepartament")
    public ResponseEntity<ResponseDTO> getKidsByDepartament(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(5)){
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());

            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    //niños por ciudad
    @GetMapping(path = "/kidsbycity")
    public ResponseEntity<ResponseDTO> getKidsBylocationsCode(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(8)){
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(200,kidsByLocationDTOList, null), HttpStatus.OK);
    }

    //______________________________________________________________________________________________________________

    //ejercicio 7 ganar posicion de niño
    @GetMapping(path = "/gainpositionkid")
    public ResponseEntity<ResponseDTO> gainPositionById(@RequestBody Map<String, Object> requestBody)
            throws ListSEException {
        String id=(String) requestBody.get("id");
        Integer gain=(Integer) requestBody.get("gain");
        listSEService.gainPositionKid(id,gain);
            return new ResponseEntity<>(new ResponseDTO(200, "el niño gano posicion", null),
                    HttpStatus.OK);

    }


    // ejercicio 8 perder posicion de niño
    @GetMapping(path = "/losepositionkid")
    public ResponseEntity<ResponseDTO>losePositionById(@RequestBody Map<String, Object> requestBody)
            throws ListSEException{
        String id=(String) requestBody.get("id");
        Integer lose=(Integer) requestBody.get("lose");
            listSEService.losePositionKid(id, lose);
        return new ResponseEntity<>(new ResponseDTO(
                200,"el niño perdion posicion",null), HttpStatus.OK);
    }

    //ejercicio 9
    @GetMapping(path = "/reportbyage")
    public ResponseEntity<ResponseDTO>reportByAge(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.reportByAge(),null), HttpStatus.OK);
    }


    //ejercicio 10 enviar niño al final si comienza su nombre por una letra dada
    @GetMapping(path = "/addToFinalNameChar/{letter}")
    public ResponseEntity<ResponseDTO>addToFinalNameChar(@PathVariable String letter){
        try{
            listSEService.addToFinalNameChar(letter);
        }catch (ListSEException e){
            return new ResponseEntity<>(new ResponseDTO(
                    200,e.getMessage(),null), HttpStatus.OK);

        }

        return new ResponseEntity<>(new ResponseDTO(
                200,"el niño ahora esta al final de la lista",null), HttpStatus.OK);
    }


    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    //obtener una informe de niños por cada ciudad con su respetivo genero
    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report =
                new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getReportKidsByLocationGendersByAge(age, report);
        return new ResponseEntity<>(new ResponseDTO(200,report, null), HttpStatus.OK);
    }

    /*
    @GetMapping(path = "/addTofinalnamechar/{letra}")
    public ResponseEntity<ResponseDTO>addToFinalNameChar(@PathVariable String letra){
        listSEService.addToFinalNameChar  (letra);
        return new ResponseEntity<>(new ResponseDTO(
                200,"niños ordenados al comienzo",null), HttpStatus.OK);
    }

     */


}
