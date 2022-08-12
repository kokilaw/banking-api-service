package io.kokilaw.banking.error;

import io.kokilaw.banking.dto.ErrorDTO;
import io.kokilaw.banking.error.exception.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Created by kokilaw on 2022-08-09
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BankingApiException.class)
    public ResponseEntity<Object> handleNotFoundException(BankingApiException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorResponse(), ex.getHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        BankingApiException constraintViolationException = BankingApiExceptions.generateConstraintViolationException(ex, request);
        return new ResponseEntity<>(constraintViolationException.getErrorResponse(), constraintViolationException.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BankingApiException bankingApiException = BankingApiExceptions.generateEntityValidationFailedException(
                ex.getTarget().getClass().getSimpleName(),
                ex.getBindingResult().getAllErrors().stream()
                        .map(error -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            return fieldName.concat(": ").concat(errorMessage);
                        }).collect(Collectors.toList())
        );
        return new ResponseEntity<>(bankingApiException.getErrorResponse(), bankingApiException.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BankingApiException invalidRequestException = BankingApiExceptions.generateInvalidRequestException(ex.getMessage());
        return new ResponseEntity<>(invalidRequestException.getErrorResponse(), invalidRequestException.getHttpStatus());
    }

}
