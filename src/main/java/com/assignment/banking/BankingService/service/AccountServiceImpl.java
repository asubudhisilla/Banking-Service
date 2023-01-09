package com.assignment.banking.BankingService.service;

import com.assignment.banking.BankingService.dto.request.MoneyTransferRequest;
import com.assignment.banking.BankingService.entity.*;
import com.assignment.banking.BankingService.exception.AccountExistException;
import com.assignment.banking.BankingService.exception.AccountNotFoundException;
import com.assignment.banking.BankingService.exception.AccountTransferException;
import com.assignment.banking.BankingService.repository.IAccountRepository;
import com.assignment.banking.BankingService.repository.ITransactionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Override
    public Account createAccount(Account account) {
        Optional<Account> existingAccount = accountRepository.searchByEmailId(account.getEmail());
        if (existingAccount.isPresent()) {
            throw new AccountExistException(String.format("Account already exists with given email=%s", account.getEmail()));
        }
        Account savedAccount = accountRepository.save(account);
        return savedAccount;
    }

    @Override
    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }


    @Override
    public List<Transactions> transfer(Account fromAccount, Account toAccount, MoneyTransferRequest moneyTransferRequest) {
        List<Transactions> transactions = new ArrayList<>();
        BigDecimal fee = new BigDecimal("0.0");
        if (fromAccount.getClosingBalance().compareTo(BigDecimal.ONE) > 0
                && fromAccount.getClosingBalance().compareTo(moneyTransferRequest.getTransferAmount()) > 0) {
            fromAccount.setClosingBalance(fromAccount.getClosingBalance().subtract(moneyTransferRequest.getTransferAmount()));
            toAccount.setClosingBalance(toAccount.getClosingBalance().add(moneyTransferRequest.getTransferAmount()));
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
            Transactions debit = Transactions.builder()
                    .account(fromAccount)
                    .transactionType(TransactionType.DEBIT)
                    .transactionRequest(TransactionRequest.TRANSFER)
                    .transactionAmount(moneyTransferRequest.getTransferAmount())
                    .status(Status.SUCCESS)
                    .message("Transaction Completed successfully")
                    .build();
            Transactions credit = Transactions.builder()
                    .account(toAccount)
                    .transactionType(TransactionType.CREDIT)
                    .transactionRequest(TransactionRequest.TRANSFER)
                    .transactionAmount(moneyTransferRequest.getTransferAmount())
                    .status(Status.SUCCESS)
                    .message("Transaction Completed successfully")
                    .build();
            transactionRepository.save(debit);
            transactionRepository.save(credit);
            transactions.add(debit);
            transactions.add(credit);
            return transactions;
        } else {
            Transactions failed = Transactions.builder()
                    .account(fromAccount)
                    .transactionType(TransactionType.DEBIT)
                    .transactionRequest(TransactionRequest.TRANSFER)
                    .transactionAmount(moneyTransferRequest.getTransferAmount())
                    .status(Status.FAILED)
                    .message("Insufficient balance to process this request")
                    .build();
            transactionRepository.save(failed);
            throw new AccountTransferException("Insufficient balance to process this request");
        }
    }

    @Override
    public Account getAccountDetailsByAccountNumber(UUID accountNumber) {
        return accountRepository.findById(accountNumber).orElseThrow(() -> new AccountNotFoundException(String.format("Account not found for id=%s", accountNumber)));
    }
}
