package com.assignment.banking.BankingService.respository;

import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.entity.CardDetails;
import com.assignment.banking.BankingService.entity.CardType;
import com.assignment.banking.BankingService.repository.IAccountRepository;
import com.assignment.banking.BankingService.repository.ICardDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICardDetailsRepository cardDetailsRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void should_find_no_account_if_repository_is_empty() {
        Iterable<Account> accounts = accountRepository.findAll();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void should_store_a_account(){
        Account account1 = Account.builder()
                .dob(LocalDate.of(1990,6,4))
                .firstName("Amar")
                .lastName("Silla")
                .address("NL")
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
                .cardDetails(CardDetails.builder()
                        .type(CardType.CREDIT)
                        .build())
                .build();
        account2 = accountRepository.save(account2);
        CardDetails card2 = account2.getCardDetails();
        card2.setAccount(account2);
        cardDetailsRepository.save(card2);


        assertThat(account1).isNotNull();
        assertThat(account2).isNotNull();
        assertThat(card1).isNotNull();
        assertThat(card2).isNotNull();
    }

    @Test
    public void should_find_all_accounts() {
        Account account1 = Account.builder()
                .dob(LocalDate.of(1990,6,4))
                .firstName("Amar")
                .lastName("Silla")
                .address("NL")
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
                .cardDetails(CardDetails.builder()
                        .type(CardType.CREDIT)
                        .build())
                .build();
        account2 = accountRepository.save(account2);
        CardDetails card2 = account2.getCardDetails();
        card2.setAccount(account2);
        cardDetailsRepository.save(card2);

        Iterable<Account> accounts = accountRepository.findAll();
        assertThat(accounts).hasSize(2);
    }
}
