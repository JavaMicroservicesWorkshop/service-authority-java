package dataart.workshop.exception.handler;

import dataart.workshop.exception.CustomerAlreadyExistException;
import dataart.workshop.exception.CustomerNotFoundException;
import dataart.workshop.exception.IncorrectPasswordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    private static final String ERROR = "error";
    private static final String ERRORS = "errorMessages";
    private static final String ERROR_MESSAGE = "errorMessage";

    private static final String BAD_REQUEST = "Bad request";
    private static final String UNAUTHORIZED = "Unauthorized";
    private static final String CONFLICT = "Conflict";
    private static final String NOT_FOUND = "Not found";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " : " + fieldError.getDefaultMessage())
                .toList();

        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, BAD_REQUEST);
        map.put(ERRORS, errors);
        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerAlreadyExistsException(CustomerAlreadyExistException exception) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, CONFLICT);
        map.put(ERROR_MESSAGE, exception.getMessage());

        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<Map<String, Object>> handleIncorrectPasswordException(IncorrectPasswordException exception) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, UNAUTHORIZED);
        map.put(ERROR_MESSAGE, exception.getMessage());

        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ERROR, NOT_FOUND);
        map.put(ERROR_MESSAGE, exception.getMessage());

        return new ResponseEntity<>(map, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

}
