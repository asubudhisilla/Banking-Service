package com.assignment.banking.BankingService.respository;

import com.assignment.banking.BankingService.entity.*;
import com.assignment.banking.BankingService.repository.AccountRepository;
import com.assignment.banking.BankingService.repository.TransactionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class TransactionsRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Test
    public void should_find_all_transactions_by_accountNumber() {
        Account account2 = Account.builder()
                .name("Ronit")
                .openingBalance(new BigDecimal("100.0"))
                .closingBalance(new BigDecimal("100.0"))
                .email("rohit@gmail.com")
                .accountNumber(UUID.randomUUID())
                .build();
        account2 = accountRepository.save(account2);
        Transactions transactions = Transactions.builder()
                .transactionType(TransactionType.DEBIT)
                .transactionRequest(TransactionRequest.TRANSFER)
                .transactionAmount(new BigDecimal(20.0))
                .status(Status.SUCCESS)
                .account(account2)
                .build();
        transactionsRepository.save(transactions);
        assertThat(transactionsRepository.findByAccountNumber(account2.getAccountNumber())).hasSize(1);
    }
}
