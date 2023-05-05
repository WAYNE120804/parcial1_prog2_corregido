package co.edu.umanizales.tads.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data

public class RequestException extends RuntimeException{
    private int code;
    private HttpStatus status;
    public RequestException(String code, String message, HttpStatus status){
        super(message);
        this.code=Integer.parseInt(code);
        this.status=status;
    }
}
