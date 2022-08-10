package io.kokilaw.banking.exception;

/**
 * Created by kokilaw on 2022-08-09
 */
public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
