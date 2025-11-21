package sgu.vbo.server.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse {
    private String message;
    private Object data;
    private boolean error;
    private HttpStatus status;

    // Mensaje sin cuerpo
    public ApiResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }


    // Mensaje con cuerpo
    public ApiResponse(String message, Object data, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }


    // Mensaje de error
    public ApiResponse(String message, boolean error, HttpStatus status) {
        this.message = message;
        this.error = error;
        this.status = status;

    }
}
