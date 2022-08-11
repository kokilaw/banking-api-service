package io.kokilaw.banking.error.exception;

/**
 * Created by kokilaw on 2022-08-09
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
