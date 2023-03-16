package co.edu.umanizales.tads.controller.dto;

import co.edu.umanizales.tads.model.Kid;
import co.edu.umanizales.tads.service.ListSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/listse")
public class ListSEController {
    @Autowired
    private ListSEService listSEService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getKids() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids(), null), HttpStatus.OK);
    }

    @GetMapping(path = "/addkid")
    public ResponseEntity<ResponseDTO> addKid() {
        return new ResponseEntity<>(new ResponseDTO(
                200, listSEService.getKids(), null), HttpStatus.OK);
    }

    @GetMapping(path = "/deletekid")
    public ResponseEntity<ResponseDTO> deleteKidByAge(byte age) {
        listSEService.deleteKidbyAge(age);
        return new ResponseEntity<>(new ResponseDTO(
                200,"niños eliminados" , null), HttpStatus.OK);
    }

    @GetMapping(path = "/gainposition")
    public ResponseEntity<ResponseDTO> gainPositionKid(String id, int gain) {
        listSEService.gainPositionKid(id, gain);
        return new ResponseEntity<>(new ResponseDTO(
                200,"el niño ha ganado posicion" , null), HttpStatus.OK);
    }

    @GetMapping(path = "/kidstart")
    public ResponseEntity<ResponseDTO> kidstar(String gender) {
        listSEService.kidStart(gender);
        return new ResponseEntity<>(new ResponseDTO(
                200,"niños puestos al comienzo y niños puestos al final" , null), HttpStatus.OK);
    }





}
