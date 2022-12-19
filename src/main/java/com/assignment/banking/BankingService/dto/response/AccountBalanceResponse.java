package com.assignment.banking.BankingService.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountBalanceResponse {
    private UUID accountNumber;
    private String firstName;
    private String lastName;
    private BigDecimal openingBalance;
    private BigDecimal closingBalance;
}
