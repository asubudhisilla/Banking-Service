package com.assignment.banking.BankingService.dto.request;

import com.assignment.banking.BankingService.validators.ValidUUID;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class MoneyWithdrawRequest {
    @ValidUUID(message = "Please Provide valid Account Number")
    private UUID accountNumber;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal amount;
}
