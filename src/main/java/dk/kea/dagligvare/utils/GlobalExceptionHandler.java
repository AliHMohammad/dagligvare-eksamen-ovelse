package dk.kea.dagligvare.utils;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String key = ((FieldError) error).getField();
            String value = error.getDefaultMessage();
            errors.put(key, value);
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleNotFoundException(BadRequestException ex) {
        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
