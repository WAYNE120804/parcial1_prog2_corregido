package co.edu.umanizales.tads.controller;

import co.edu.umanizales.tads.controller.dto.ErrorDTO;
import co.edu.umanizales.tads.controller.dto.ResponseDTO;
import co.edu.umanizales.tads.exception.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorDTO>rutinmeExceptionHandler(RuntimeException ex){
        ErrorDTO errorDTO=ErrorDTO.builder().code(400).message(ex.getMessage()).build();
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO>requestExceptionHandler(RequestException ex){
        List<ErrorDTO>errorDTOS=new ArrayList<>();
        ErrorDTO errorDTO=ErrorDTO.builder().code(ex.getCode()).message(ex.getMessage()).build();
        errorDTOS.add(errorDTO);
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO>handleMethodArgumentValid(MethodArgumentNotValidException ex){
        List<ErrorDTO> errorDTOS=new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            ErrorDTO errorDTO=ErrorDTO.builder().code(HttpStatus.BAD_REQUEST.value()).
                    message(error.getDefaultMessage()).build();
            errorDTOS.add(errorDTO);
        });
        return new ResponseEntity<>(new ResponseDTO(HttpStatus.BAD_REQUEST.value(),"hay un error en los elementos",
                errorDTOS),HttpStatus.BAD_REQUEST);
    }
}
