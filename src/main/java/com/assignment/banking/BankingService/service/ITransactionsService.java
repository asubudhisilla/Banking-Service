package com.assignment.banking.BankingService.service;

import com.assignment.banking.BankingService.entity.Transactions;

import java.util.List;
import java.util.UUID;

public interface ITransactionsService {
    Transactions createTransaction(Transactions transaction);

    List<Transactions> findAllTransactionByAccountId(UUID accountNumber);
}
