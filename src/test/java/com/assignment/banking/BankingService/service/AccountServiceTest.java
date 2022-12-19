package com.assignment.banking.BankingService.service;

import com.assignment.banking.BankingService.dto.request.MoneyTransferRequest;
import com.assignment.banking.BankingService.dto.request.MoneyWithdrawRequest;
import com.assignment.banking.BankingService.entity.*;
import com.assignment.banking.BankingService.exception.AccountExistException;
import com.assignment.banking.BankingService.exception.AccountNotFoundException;
import com.assignment.banking.BankingService.exception.AccountTransferException;
import com.assignment.banking.BankingService.exception.AccountWithdrawException;
import com.assignment.banking.BankingService.repository.IAccountRepository;
import com.assignment.banking.BankingService.repository.ICardDetailsRepository;
import com.assignment.banking.BankingService.repository.ITransactionsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private IAccountRepository accountRepository;
    @Mock
    private ICardDetailsRepository cardDetailsRepository;
    @Mock
    private ITransactionsRepository transactionsRepository;

    private AccountServiceImpl accountService;
    private Account account;
    private Transactions transactions;
    private CardDetails cardDetails;
    private MoneyWithdrawRequest moneyWithdrawRequest;
    private MoneyTransferRequest moneyTransferRequest;

    @BeforeEach
    public void setup() {
        cardDetails = CardDetails.builder()
                .cardNumber(UUID.randomUUID())
                .type(CardType.CREDIT)
                .build();
        account = Account.builder()
                .accountNumber(UUID.randomUUID())
                .dob(LocalDate.of(1990,6,4))
                .firstName("Amar")
                .lastName("Silla")
                .address("NL")
                .openingBalance(new BigDecimal(100.00))
                .closingBalance(new BigDecimal(100.00))
                .cardDetails(cardDetails)
                .build();
        transactions = Transactions.builder()
                .transactionId(UUID.randomUUID())
                .transactionType(TransactionType.DEBIT)
                .transactionRequest(TransactionRequest.WITHDRAW)
                .transactionAmount(new BigDecimal(20.0))
                .status(Status.SUCCESS)
                .transactionFee(new BigDecimal(2.0))
                .account(account)
                .build();
        accountService = new AccountServiceImpl(accountRepository, transactionsRepository, cardDetailsRepository);
        moneyWithdrawRequest = MoneyWithdrawRequest.builder()
                .accountNumber(account.getAccountNumber())
                .amount(new BigDecimal(20.00))
                .build();
        moneyTransferRequest = MoneyTransferRequest.builder()
                .toAccount(account.getAccountNumber())
                .fromAccount(account.getAccountNumber())
                .transferAmount(new BigDecimal(20.00))
                .build();
    }

    @Test
    public void givenNonExistingAccount_whenSaveAccount_thenReturnAccount() {

        when(accountRepository.searchByFirstAndLastName(any(String.class), any(String.class))).thenReturn(Optional.empty());
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        // when -  action or the behaviour that we are going test
        Account savedAccount = accountService.createAccount(account);

        // then - verify the output
        assertThat(savedAccount).isNotNull();
    }

    @Test
    public void givenExistingAccount_whenSaveAccount_thenThrowsException() {

        when(accountRepository.searchByFirstAndLastName(any(String.class), any(String.class))).thenReturn(Optional.of(account));
        assertThrows(AccountExistException.class, () -> accountService.createAccount(account));
    }

    @Test
    public void givenAccountList_whenGetAllAccount_thenReturnAccountList() {

        when(accountRepository.findAll()).thenReturn(List.of(account));
        // when -  action or the behaviour that we are going test
        List<Account> accounts = accountService.getAllAccount();

        // then - verify the output
        assertThat(accounts).isNotNull();
        assertThat(accounts.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyAccountList_whenGetAllAccount_thenReturnEmptyAccountList() {

        when(accountRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        // when -  action or the behaviour that we are going test
        List<Account> accounts = accountService.getAllAccount();

        // then - verify the output
        assertThat(accounts).isEmpty();
        assertThat(0).isEqualTo(0);
    }

    @Test
    public void givenAccountId_whenGetAccountById_thenReturnAccount() {

        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.of(account));
        // when -  action or the behaviour that we are going test
        Account savedAccount = accountService.getAccountDetailsByAccountNumber(account.getAccountNumber());
        // then - verify the output
        assertThat(savedAccount).isNotNull();
    }

    @Test
    public void givenMissingAccountId_whenGetAccountById_thenThrowsException() {
        when(accountRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        // when -  action or the behaviour that we are going test
        assertThrows(AccountNotFoundException.class, () -> accountService.getAccountDetailsByAccountNumber(account.getAccountNumber()));
    }

    @Test
    public void givenWithdrawRequest_whenWithdrawIsValid_thenReturnTransaction(){
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(transactionsRepository.save(any(Transactions.class))).thenReturn(transactions);
        Transactions withdrawTxn = accountService.withdraw(account,moneyWithdrawRequest);
        assertThat(withdrawTxn).isNotNull();
    }

    @Test
    public void givenWithdrawRequest_whenWithdrawIsInvalid_thenThrowsException(){
        moneyWithdrawRequest.setAmount(new BigDecimal(120.00));
        assertThrows(AccountWithdrawException.class, () -> accountService.withdraw(account,moneyWithdrawRequest));
    }

    @Test
    public void givenTransferRequest_whenTransferIsValid_thenReturnTransactionList(){
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        when(transactionsRepository.save(any(Transactions.class))).thenReturn(transactions);
        List<Transactions> withdrawTxn = accountService.transfer(account,account,moneyTransferRequest);
        assertThat(withdrawTxn).isNotNull();
    }

    @Test
    public void givenTransferRequest_whenTransferIsInValid_thenThrowsException(){
        moneyTransferRequest.setTransferAmount(new BigDecimal(120.00));
        assertThrows(AccountTransferException.class, () -> accountService.transfer(account,account,moneyTransferRequest));
    }

}
