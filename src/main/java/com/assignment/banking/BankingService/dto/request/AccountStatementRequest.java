package com.assignment.banking.BankingService.dto.request;

import com.assignment.banking.BankingService.validators.ValidUUID;
import lombok.Data;
import java.util.UUID;

@Data
public class AccountStatementRequest {
    @ValidUUID(message = "Please Provide valid Account Number")
    private UUID accountNumber;
}
