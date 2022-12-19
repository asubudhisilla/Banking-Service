package com.assignment.banking.BankingService.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
