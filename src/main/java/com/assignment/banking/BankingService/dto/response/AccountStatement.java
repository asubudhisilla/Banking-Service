package com.assignment.banking.BankingService.dto.response;

import com.assignment.banking.BankingService.entity.Transactions;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AccountStatement {
    private UUID accountNumber;
    private String firstName;
    private List<Transactions> transactionList;
}
