package com.assignment.banking.BankingService.dto.request;

import com.assignment.banking.BankingService.validators.ValidUUID;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MoneyTransferRequest {
    @ValidUUID(message = "Please Provide valid From Account Number")
    private UUID fromAccount;
    @ValidUUID(message = "Please Provide valid To Account Number")
    private UUID toAccount;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal transferAmount;
}
