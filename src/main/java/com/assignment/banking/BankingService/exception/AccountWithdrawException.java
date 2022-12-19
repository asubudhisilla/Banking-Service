package com.assignment.banking.BankingService.exception;

public class AccountWithdrawException extends RuntimeException{

    public AccountWithdrawException(String message) {
        super(message);
    }

    public AccountWithdrawException(Throwable cause) {
        super(cause);
    }

    public AccountWithdrawException(String message, Throwable cause) {
        super(message, cause);
    }
}
