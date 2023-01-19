package com.assignment.banking.BankingService.respository;

import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;


    @Test
    public void should_find_no_account_if_repository_is_empty() {
        Iterable<Account> accounts = accountRepository.findAll();
        assertThat(accounts).isEmpty();
    }

    @Test
    public void should_store_a_account(){
        Account account1 = Account.builder()
                .name("Amar")
                .openingBalance(new BigDecimal("100.0"))
                .closingBalance(new BigDecimal("100.0"))
                .email("amars400@gmail.com")
                .accountNumber(UUID.randomUUID())
                .build();
        account1 = accountRepository.save(account1);

        Account account2 = Account.builder()
                .name("Ronit")
                .openingBalance(new BigDecimal("100.0"))
                .closingBalance(new BigDecimal("100.0"))
                .email("rohit@gmail.com")
                .accountNumber(UUID.randomUUID())
                .build();
        account2 = accountRepository.save(account2);
        assertThat(account1).isNotNull();
        assertThat(account2).isNotNull();
    }

    @Test
    public void should_find_all_accounts() {
        Account account1 = Account.builder()
                .name("Amar")
                .openingBalance(new BigDecimal("100.0"))
                .closingBalance(new BigDecimal("100.0"))
                .email("amars400@gmail.com")
                .build();
        accountRepository.save(account1);

        Account account2 = Account.builder()
                .name("Ronit")
                .openingBalance(new BigDecimal("100.0"))
                .closingBalance(new BigDecimal("100.0"))
                .email("rohit@gmail.com")
                .build();
        accountRepository.save(account2);
        Iterable<Account> accounts = accountRepository.findAll();
        assertThat(accounts).hasSize(2);
    }
}
