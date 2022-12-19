package com.assignment.banking.BankingService.service;

import com.assignment.banking.BankingService.dto.request.MoneyTransferRequest;
import com.assignment.banking.BankingService.dto.request.MoneyWithdrawRequest;
import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.entity.Transactions;

import java.util.List;
import java.util.UUID;

public interface IAccountService {

    Account createAccount(Account account);

    List<Account> getAllAccount();

    Transactions withdraw(Account account, MoneyWithdrawRequest moneyWithdrawRequest);

    List<Transactions> transfer(Account fromAccount, Account toAccount, MoneyTransferRequest moneyTransferRequest);

    Account getAccountDetailsByAccountNumber(UUID accountNumber);
}
