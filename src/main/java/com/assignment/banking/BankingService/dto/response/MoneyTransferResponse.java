package com.assignment.banking.BankingService.dto.response;

import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.entity.Transactions;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MoneyTransferResponse {
    private UUID senderAccountNumber;
    private String senderFirstName;
    private BigDecimal senderAccountBalance;
    private Transactions senderTransaction;
    private UUID receiverAccountNumber;
    private String receiverFirstName;
    private BigDecimal receiverAccountBalance;
    private Transactions receiverTransaction;

}
