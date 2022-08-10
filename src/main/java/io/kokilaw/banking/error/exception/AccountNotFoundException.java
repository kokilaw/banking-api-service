package io.kokilaw.banking.error.exception;

/**
 * Created by kokilaw on 2022-08-09
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
