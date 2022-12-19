package com.assignment.banking.BankingService.exception;

public class AccountExistException extends RuntimeException {

    public AccountExistException() {
        super();
    }

    public AccountExistException(String message) {
        super(message);
    }

    public AccountExistException(Throwable cause) {
        super(cause);
    }

    public AccountExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
