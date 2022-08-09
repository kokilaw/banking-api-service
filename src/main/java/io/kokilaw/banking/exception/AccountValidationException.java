package io.kokilaw.banking.exception;

/**
 * Created by kokilaw on 2022-08-09
 */
public class AccountValidationException extends RuntimeException {
    public AccountValidationException(String message) {
        super(message);
    }
}
