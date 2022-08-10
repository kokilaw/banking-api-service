package io.kokilaw.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by kokilaw on 2022-08-09
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", "Account not found");
        responseBody.put("additionalInfo", ex.getMessage());
        responseBody.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountValidationException.class)
    public ResponseEntity<Object> handleAccountValidationFailedException(AccountValidationException ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", "Account validation failed");
        responseBody.put("additionalInfo", ex.getMessage());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CurrencyNotSupportedException.class)
    public ResponseEntity<Object> handleCurrencyCodeNotSupportedException(CurrencyNotSupportedException ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", "Currency code not supported");
        responseBody.put("additionalInfo", ex.getMessage());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }


}
