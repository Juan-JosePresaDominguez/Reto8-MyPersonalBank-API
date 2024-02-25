package es.netmind.mypersonalbankapi.config;

import com.fasterxml.jackson.databind.JsonMappingException;
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

    // CAPTURA DE EXCEPCIONES PERSONALIZADAS ------------------------------------------
    // @ExceptionHandler - Mecanismo de captura de excepción
    // Devuelve un ResponseEntity
    @ExceptionHandler(value = ClienteNotFoundException.class)
    public ResponseEntity<Object> handleClienteNotfoundException(ClienteNotFoundException exception) {
        System.out.println(">>> EXCEPCIÓN: ClienteNotFoundException");
        //return new ResponseEntity<>("Cliente not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND); // Muestra el mensaje que le envía la excepción de ClientesControllerAPI.
    }

    @ExceptionHandler(value = JsonMappingException.class)
    public ResponseEntity<Object> handleJsonMappingException(JsonMappingException exception) {
        System.out.println(">>> EXCEPCIÓN: JsonMappingException");
        //return new ResponseEntity<>("Cliente not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Error JSON (envío recurso BODY mal formado) --> " + exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); // Muestra el mensaje que le envía la excepción de ClientesControllerAPI.
    }

    // CAPTURA DE EXCEPCIONES DE VALIDACIÓN (BODY Y PARÁMETROS) -----------------------
    // @ResponseStatus para modificar el código de estado/respuesta. Dado que estamos devolviendo un objeto, no un ResponseEntity, podemos añadir un código explícito de respuesta.
    // Devuelve un objeto de tipo Map (clave - valor)
    // Excepción 'MethodArgumentNotValidException' --> El argumento del método no es válido: aparece cuando envío un recurso (Cliente) mal formado (BODY)
    //@ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 400 Demasiado genérico
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED) // HTTP 412
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        System.out.println(">>> EXCEPCIÓN: MethodArgumentNotValidException");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    // Excepción 'ConstraintViolationException' --> Violación de la constricción/restricción: aparece cuando hay un constraint/restricción como @Min(1), por ejemplo. Cuando la condición de validación de un parámetro no se cumple.
    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        System.out.println(">>> EXCEPCIÓN: ConstraintViolationException");
        //return new ResponseEntity<>("Not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST); // HTTP 400
        return new ResponseEntity<>("No válido por error de validación: " + e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY); // HTTP 422
    }

}

