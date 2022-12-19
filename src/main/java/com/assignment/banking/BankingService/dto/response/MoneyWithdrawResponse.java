package com.assignment.banking.BankingService.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class MoneyWithdrawResponse {
    private UUID accountNumber;
    private String firstName;
    private UUID transactionNumber;
    private String status;
    private BigDecimal currentBalance;
}
