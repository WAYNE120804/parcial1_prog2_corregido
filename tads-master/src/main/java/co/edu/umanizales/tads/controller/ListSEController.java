package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
import co.edu.umanizales.tads.controller.dto.ReportKidsLocationGenderDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.model.Location;
import co.edu.umanizales.tads.service.ListSEService;
import co.edu.umanizales.tads.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;
    @Autowired
    private LocationService locationService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getKids(){
        return new ResponseEntity<>(new ResponseDTO(
                200,listSEService.getKids(),null), HttpStatus.OK);
    }

    //invertir lista
    @GetMapping("/invert")
    public ResponseEntity<ResponseDTO> invert(){
        listSEService.invert();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE ha invertido la lista",
                null), HttpStatus.OK);

    }

    @GetMapping(path = "/change_extremes")
    public ResponseEntity<ResponseDTO> changeExtremes() {
        listSEService.changeExtremes();
        return new ResponseEntity<>(new ResponseDTO(
                200,"SE han intercambiado los extremos",
                null), HttpStatus.OK);
    }

    //adicionar niños
    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
       if(location == null){
           return new ResponseEntity<>(new ResponseDTO(
                   404,"La ubicación no existe",
                   null), HttpStatus.OK);
       }
           listSEService.add(new Kid(kidDTO.getIdentification(), kidDTO.getName(), kidDTO.getAge(),
                   kidDTO.getGender(), location));
           return new ResponseEntity<>(new ResponseDTO(200, "Se ha adicionado el petacón", null),
                   HttpStatus.OK);

    }

    //borrar niño por edad
    @GetMapping(path = "/deletekid/{age}")
    public ResponseEntity<ResponseDTO>deleteKidByAge(@PathVariable byte age){
        listSEService.deleteKidbyAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200,"niño eliminado",null), HttpStatus.OK);
    }

    //ganar posicion de niño
    @GetMapping(path = "/gainpositionkid/{id}/{gain}")
    public ResponseEntity<ResponseDTO>gainPositionById(@PathVariable String id,@PathVariable int gain){
        listSEService.gainPositionKid(id, gain);
        return new ResponseEntity<>(new ResponseDTO(
                200,"nel niño gano posicion",null), HttpStatus.OK);
    }

    //niños al comienzo
    @GetMapping(path = "/orderboystostart")
    public ResponseEntity<ResponseDTO>orderBoysToStart(){
        listSEService.orderBoysToStart();
        return new ResponseEntity<>(new ResponseDTO(
                200,"niños ordenados al comienzo",null), HttpStatus.OK);
    }

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
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
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
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }



    //obtener una informe de niños por cada ciudad con su respetivo genero
    @GetMapping(path = "/kidsbylocationgenders/{age}")
    public ResponseEntity<ResponseDTO> getReportKisLocationGenders(@PathVariable byte age) {
        ReportKidsLocationGenderDTO report =
                new ReportKidsLocationGenderDTO(locationService.getLocationsByCodeSize(8));
        listSEService.getReportKidsByLocationGendersByAge(age, report);
        return new ResponseEntity<>(new ResponseDTO(
                200,report,
                null), HttpStatus.OK);
    }


}
