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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ITransactionsRepository transactionRepository;
    @Autowired
    private ICardDetailsRepository cardDetailsRepository;

    @Override
    public Account createAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.searchByFirstAndLastName(account.getFirstName(), account.getLastName());
        if (existingAccount.isPresent()) {
            throw new AccountExistException(String.format("Account already exists with given firstName=%s and lastName=%s", account.getFirstName(), account.getLastName()));
        }
        Account savedAccount = accountRepository.save(account);
        CardDetails card = account.getCardDetails();
        card.setAccount(account);
        cardDetailsRepository.save(card);
        return savedAccount;
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public Transactions withdraw(Account account, MoneyWithdrawRequest moneyWithdrawRequest) {
        BigDecimal fee = new BigDecimal("0.0");
        if ("Credit".equals(account.getCardDetails().getType().getCardType())) {
            fee = moneyWithdrawRequest.getAmount().divide(new BigDecimal("100.0"));
        }
        BigDecimal totalAmount = moneyWithdrawRequest.getAmount().add(fee);
        if (account.getClosingBalance().compareTo(BigDecimal.ONE) > 0
                && account.getClosingBalance().compareTo(totalAmount) > 0) {
            account.setClosingBalance(account.getClosingBalance().subtract(totalAmount));
            accountRepository.save(account);
            Transactions transaction = Transactions.builder()
                    .account(account)
                    .transactionType(TransactionType.DEBIT)
                    .transactionRequest(TransactionRequest.WITHDRAW)
                    .transactionFee(fee)
                    .transactionAmount(moneyWithdrawRequest.getAmount())
                    .status(Status.SUCCESS)
                    .build();
            transactionRepository.save(transaction);
            return transaction;
        } else {
            throw new AccountWithdrawException("Insufficient balance to process this request");
        }
    }

    @Override
    public List<Transactions> transfer(Account fromAccount, Account toAccount, MoneyTransferRequest moneyTransferRequest) {
        List<Transactions> transactions = new ArrayList<>();
        BigDecimal fee = new BigDecimal("0.0");
        if ("Credit".equals(fromAccount.getCardDetails().getType().getCardType())) {
            fee = moneyTransferRequest.getTransferAmount().divide(new BigDecimal("100.0"));
        }
        BigDecimal totalAmount = moneyTransferRequest.getTransferAmount().add(fee);
        if (fromAccount.getClosingBalance().compareTo(BigDecimal.ONE) > 0
                && fromAccount.getClosingBalance().compareTo(totalAmount) > 0) {
            fromAccount.setClosingBalance(fromAccount.getClosingBalance().subtract(totalAmount));
            toAccount.setClosingBalance(toAccount.getClosingBalance().add(moneyTransferRequest.getTransferAmount()));
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            Transactions debit = Transactions.builder()
                    .account(fromAccount)
                    .transactionType(TransactionType.DEBIT)
                    .transactionRequest(TransactionRequest.TRANSFER)
                    .transactionFee(fee)
                    .transactionAmount(moneyTransferRequest.getTransferAmount())
                    .status(Status.SUCCESS)
                    .build();
            Transactions credit = Transactions.builder()
                    .account(toAccount)
                    .transactionType(TransactionType.CREDIT)
                    .transactionRequest(TransactionRequest.TRANSFER)
                    .transactionFee(fee)
                    .transactionAmount(moneyTransferRequest.getTransferAmount())
                    .status(Status.SUCCESS)
                    .build();
            transactionRepository.save(debit);
            transactionRepository.save(credit);
            transactions.add(debit);
            transactions.add(credit);
            return transactions;
        } else {
            throw new AccountTransferException("Insufficient balance to process this request");
        }
    }

    @Override
    public Account getAccountDetailsByAccountNumber(UUID accountNumber) {
        return accountRepository.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(String.format("Account not found for id=%s", accountNumber)));
    }
}
