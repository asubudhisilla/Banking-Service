package com.assignment.banking.BankingService.respository;

import com.assignment.banking.BankingService.entity.*;
import com.assignment.banking.BankingService.repository.IAccountRepository;
import com.assignment.banking.BankingService.repository.ICardDetailsRepository;
import com.assignment.banking.BankingService.repository.ITransactionsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class TransactionsRepositoryTest {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICardDetailsRepository cardDetailsRepository;

    @Autowired
    private ITransactionsRepository transactionsRepository;

    @Test
    public void should_find_all_transactions_by_accountNumber() {
        Account account1 = Account.builder()
                .dob(LocalDate.of(1990,6,4))
                .firstName("Amar")
                .lastName("Silla")
                .address("NL")
                .openingBalance(new BigDecimal(100.00))
                .closingBalance(new BigDecimal(100.00))
                .cardDetails(CardDetails.builder()
                        .type(CardType.DEBIT)
                        .build())
                .build();
        account1 = accountRepository.save(account1);
        CardDetails card1 = account1.getCardDetails();
        card1.setAccount(account1);
        cardDetailsRepository.save(card1);

        Account account2 = Account.builder()
                .dob(LocalDate.of(1990,6,4))
                .firstName("Amar")
                .lastName("Silla")
                .address("NL")
                .openingBalance(new BigDecimal(100.00))
                .closingBalance(new BigDecimal(100.00))
                .cardDetails(CardDetails.builder()
                        .type(CardType.CREDIT)
                        .build())
                .build();
        account2 = accountRepository.save(account2);
        CardDetails card2 = account2.getCardDetails();
        card2.setAccount(account2);
        cardDetailsRepository.save(card2);

        Iterable<Account> accounts = accountRepository.findAll();

        Transactions transactions = Transactions.builder()
                .transactionType(TransactionType.DEBIT)
                .transactionRequest(TransactionRequest.WITHDRAW)
                .transactionAmount(new BigDecimal(20.0))
                .status(Status.SUCCESS)
                .transactionFee(new BigDecimal(2.0))
                .account(account2)
                .build();
        transactionsRepository.save(transactions);
        assertThat(transactionsRepository.findByAccountNumber(account2.getAccountNumber())).hasSize(1);

    }
}
