package es.netmind.mypersonalbankapi.config;

import es.netmind.mypersonalbankapi.exceptions.ClienteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Maneja las excepciones globalmente
public class ConfigExceptionController {

    //Capturador de Excepciones
    @ExceptionHandler(value = ClienteNotFoundException.class)
    public ResponseEntity<Object> handleClienteNotfoundException(ClienteNotFoundException exception) {
        //return new ResponseEntity<>("Cliente not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND); // Muestra el mensaje que le envía la excepción de ClientesControllerAPI.
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED) // HTTP 412
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("Not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST); // HTTP 400
    }

}

