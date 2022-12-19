package com.assignment.banking.BankingService.exception;

public class AccountTransferException extends RuntimeException{

    public AccountTransferException(String message) {
        super(message);
    }

    public AccountTransferException(Throwable cause) {
        super(cause);
    }

    public AccountTransferException(String message, Throwable cause) {
        super(message, cause);
    }
}
