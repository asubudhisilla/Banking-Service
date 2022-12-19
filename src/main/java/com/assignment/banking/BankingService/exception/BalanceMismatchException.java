package com.assignment.banking.BankingService.exception;

public class BalanceMismatchException extends RuntimeException {

    public BalanceMismatchException() {
        super();
    }

    public BalanceMismatchException(String message) {
        super(message);
    }

    public BalanceMismatchException(Throwable cause) {
        super(cause);
    }

    public BalanceMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}

