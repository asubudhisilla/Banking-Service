package com.assignment.banking.BankingService.service;

import com.assignment.banking.BankingService.entity.Transactions;
import com.assignment.banking.BankingService.repository.TransactionsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionRepository;

    @Override
    public Transactions createTransaction(Transactions transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transactions> findAllTransactionByAccountId(UUID accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}
