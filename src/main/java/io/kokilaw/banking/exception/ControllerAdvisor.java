package io.kokilaw.banking.exception;

import io.kokilaw.banking.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by kokilaw on 2022-08-09
 */
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Account not found.");
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

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Object> handleTransactionNotFoundException(TransactionNotFoundException ex, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage("Transaction not found.");
        errorDTO.setStatusCode(httpStatus.value());
        errorDTO.setAdditionalInfo(ex.getMessage());
        return new ResponseEntity<>(errorDTO, httpStatus);
    }


}
