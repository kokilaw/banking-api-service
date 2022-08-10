package io.kokilaw.banking.error.exception;

/**
 * Created by kokilaw on 2022-08-11
 */
public class CurrencyNotSupportedException extends RuntimeException {
    public CurrencyNotSupportedException(String message) {
        super(message);
    }
}
