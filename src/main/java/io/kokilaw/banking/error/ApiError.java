package io.kokilaw.banking.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Created by kokilaw on 2022-08-11
 */
@AllArgsConstructor
@Getter
public enum ApiError {

    CURRENCY_NOT_SUPPORTED_ERROR("E100001", "Provided currency is not supported.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_ACCOUNT_BALANCE_ERROR("E100002", "Insufficient account balance.", HttpStatus.BAD_REQUEST),
    ENTITY_VALIDATION_FAILED_ERROR("E100003", "Entity validation failed.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("E100004", "Invalid Request.", HttpStatus.BAD_REQUEST),
    ENTITY_NOT_FOUND_ERROR("E200001", "Entity not found.", HttpStatus.NOT_FOUND),
    TRANSACTION_FAILED_ERROR("E300001", "Transaction failed.", HttpStatus.INTERNAL_SERVER_ERROR),
    CONSTRAINT_VIOLATION("E100002", "Constrain Violation.", HttpStatus.INTERNAL_SERVER_ERROR);

    private String errorCode;
    private String errorMessage;
    private HttpStatus httpStatus;

}
