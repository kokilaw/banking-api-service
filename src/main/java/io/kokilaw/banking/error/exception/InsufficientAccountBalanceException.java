package io.kokilaw.banking.error.exception;

/**
 * Created by kokilaw on 2022-08-11
 */
public class InsufficientAccountBalanceException extends RuntimeException {
    public InsufficientAccountBalanceException(String message) {
        super(message);
    }
}
