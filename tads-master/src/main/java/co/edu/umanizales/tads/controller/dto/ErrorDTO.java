package co.edu.umanizales.tads.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ErrorDTO {
    private int code;
    private String message;

    public ErrorDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
