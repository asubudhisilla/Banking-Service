package com.assignment.banking.BankingService.service;

import com.assignment.banking.BankingService.entity.Transactions;
import com.assignment.banking.BankingService.repository.ITransactionsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class TransactionsServiceImpl implements ITransactionsService {

    @Autowired
    private ITransactionsRepository transactionRepository;

    @Override
    public Transactions createTransaction(Transactions transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transactions> findAllTransactionByAccountId(UUID accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}
