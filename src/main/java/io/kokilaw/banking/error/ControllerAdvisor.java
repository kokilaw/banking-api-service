package io.kokilaw.banking.error;

import io.kokilaw.banking.dto.ErrorDTO;
import io.kokilaw.banking.error.exception.NotFoundException;
import io.kokilaw.banking.error.exception.CurrencyNotSupportedException;
import io.kokilaw.banking.error.exception.InsufficientAccountBalanceException;
import io.kokilaw.banking.error.exception.TransactionNotFoundException;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Entity not found.");
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setAdditionalInfo(ex.getMessage());
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @ExceptionHandler(CurrencyNotSupportedException.class)
    public ResponseEntity<Object> handleCurrencyCodeNotSupportedException(CurrencyNotSupportedException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Currency code not supported.");
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setAdditionalInfo(ex.getMessage());
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @ExceptionHandler(InsufficientAccountBalanceException.class)
    public ResponseEntity<Object> handleInsufficientAccountBalanceException(InsufficientAccountBalanceException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Account balance not sufficient.");
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setAdditionalInfo(ex.getMessage());
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Validation error.");
        errorDTO.setAdditionalInfo(String.format("%s validation failed", ex.getTarget().getClass().getSimpleName()));
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setSubErrors(ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName.concat(": ").concat(errorMessage);
                }).collect(Collectors.toList()));
        return new ResponseEntity<>(errorDTO, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Invalid request received.");
        errorDTO.setAdditionalInfo(ex.getMessage());
        errorDTO.setStatusCode(status.value());
        return new ResponseEntity<>(errorDTO, status);
    }

}
