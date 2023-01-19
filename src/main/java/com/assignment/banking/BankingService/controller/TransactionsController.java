package com.assignment.banking.BankingService.controller;

import com.assignment.banking.BankingService.dto.response.AccountStatement;
import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.service.AccountService;
import com.assignment.banking.BankingService.service.TransactionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/banking/transactions")
public class TransactionsController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<AccountStatement> getAllTransactionsByAccountNumber(@RequestParam(name = "accountNumber") UUID accountNumber) {
        logger.info("GET request to fetch Transactions details by account number=%s", accountNumber);
        Account account = accountService.getAccountDetailsByAccountNumber(accountNumber);
        AccountStatement accountStatement = AccountStatement.builder()
                .accountNumber(account.getAccountNumber())
                .firstName(account.getName())
                .transactionList(transactionsService.findAllTransactionByAccountId(accountNumber))
                .build();
        return ResponseEntity.ok().body(accountStatement);
    }
}
