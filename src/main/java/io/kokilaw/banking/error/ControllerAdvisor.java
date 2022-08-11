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
        if (ex.getConstraintName().equals("positive_account_balance")) {
            BankingApiException inSufficientAccountBalanceException = BankingApiExceptions.generateInSufficientAccountBalanceException(null);
            return new ResponseEntity<>(inSufficientAccountBalanceException.getErrorResponse(), inSufficientAccountBalanceException.getHttpStatus());
        }
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Constraint Violation");
        errorDTO.setStatusCode(httpStatus.value());
        return new ResponseEntity<>(errorDTO, httpStatus);
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
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Invalid request received.");
        errorDTO.setAdditionalInfo(ex.getMessage());
        errorDTO.setStatusCode(status.value());
        return new ResponseEntity<>(errorDTO, status);
    }

}
