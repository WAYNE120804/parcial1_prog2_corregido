package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.KidDTO;
import co.edu.umanizales.tads.controller.dto.KidsByLocationDTO;
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

    @PostMapping
    public ResponseEntity<ResponseDTO> addKid(@RequestBody KidDTO kidDTO){
        Location location = locationService.getLocationByCode(kidDTO.getCodeLocation());
       if(location == null){
           return new ResponseEntity<>(new ResponseDTO(
                   404,"La ubicación no existe",
                   null), HttpStatus.OK);
       }
       /*
       if(listSEService.getKids().getData().getIdentification()!= kidDTO.getIdentification()) {
           */
           listSEService.add(
                   new Kid(kidDTO.getIdentification(),
                           kidDTO.getName(), kidDTO.getAge(),
                           kidDTO.getGender(), location));
           return new ResponseEntity<>(new ResponseDTO(
                   200, "Se ha adicionado el petacón", null), HttpStatus.OK);

            /*
       }else{
           return new ResponseEntity<>(new ResponseDTO(
                   404,"la identificacion ya existe",
                   null), HttpStatus.OK);
       }
             */

    }

    @GetMapping(path = "/kidsbycity")
    public ResponseEntity<ResponseDTO> getKidsByCity(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
            int countm=0;
            int countf=0;
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count,countm, countf));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbydepartament")
    public ResponseEntity<ResponseDTO> getKidsByDepartament(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(5)){
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
            int countm=0;
            int countf=0;
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count,countm, countf));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbylocations")
    public ResponseEntity<ResponseDTO> getKidsBylocationsCode(){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocationsByCodeSize(8)){
            int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
            int countm=0;
            int countf=0;
            if(count>0){
                kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count,countm, countf));
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletekid/{age}")
    public ResponseEntity<ResponseDTO>deleteKidByAge(@PathVariable byte age){
        listSEService.deleteKidbyAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200,"niño eliminado",null), HttpStatus.OK);
    }

    @GetMapping(path = "/gainpositionkid")
    public ResponseEntity<ResponseDTO>gainPositionById(String id, int gain){
        listSEService.gainPositionKid(id, gain);
        return new ResponseEntity<>(new ResponseDTO(
                200,"nel niño gano posicion",null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidsbycityandgender/{age}")
    public ResponseEntity<ResponseDTO> getCountKidsBylocationAndGender(@PathVariable byte age){
        List<KidsByLocationDTO> kidsByLocationDTOList = new ArrayList<>();
        for(Location loc: locationService.getLocations()){
            if(listSEService.getKids().getData().getAge()>age) {
                int count = listSEService.getCountKidsBylocationsCode(loc.getCode());
                int countm=listSEService.getCountKidsBylocationAndGenderM(loc.getCode());
                int countf=listSEService.getCountKidsBylocationAndGenderF(loc.getCode());
                if (count > 0) {
                    kidsByLocationDTOList.add(new KidsByLocationDTO(loc,count, countm, countf));
                }
            }
        }
        return new ResponseEntity<>(new ResponseDTO(
                200,kidsByLocationDTOList,
                null), HttpStatus.OK);
    }


}
