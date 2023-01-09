package com.assignment.banking.BankingService.service;

import com.assignment.banking.BankingService.entity.*;
import com.assignment.banking.BankingService.repository.ITransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private ITransactionsRepository transactionRepository;
    private Account account;
    private Transactions transactions;
    private TransactionsServiceImpl transactionsService;

    @BeforeEach
    public void setup() {
        account = Account.builder()
                .name("Amar")
                .openingBalance(new BigDecimal("100.0"))
                .closingBalance(new BigDecimal("100.0"))
                .email("amars400@gmail.com")
                .accountNumber(UUID.randomUUID())
                .build();
        transactions = Transactions.builder()
                .transactionId(UUID.randomUUID())
                .transactionType(TransactionType.DEBIT)
                .transactionRequest(TransactionRequest.TRANSFER)
                .transactionAmount(new BigDecimal(20.0))
                .status(Status.SUCCESS)
                .account(account)
                .build();
        transactionsService = new TransactionsServiceImpl(transactionRepository);
    }

    @Test
    public void givenTransactionForAccount_whenCreateTransaction_thenReturnTransaction() {

        when(transactionRepository.save(any(Transactions.class))).thenReturn(transactions);
        // when -  action or the behaviour that we are going test
        Transactions savedTransaction = transactionsService.createTransaction(transactions);

        // then - verify the output
        assertThat(savedTransaction).isNotNull();
    }

    @Test
    public void givenTransactionList_whenGetAllTransaction_thenReturnTransactionList() {

        when(transactionRepository.findByAccountNumber(any(UUID.class))).thenReturn(List.of(transactions));
        // when -  action or the behaviour that we are going test
        List<Transactions> transactionsList = transactionsService.findAllTransactionByAccountId(account.getAccountNumber());

        // then - verify the output
        assertThat(transactionsList).isNotNull();
        assertThat(transactionsList.size()).isEqualTo(1);
    }
}
