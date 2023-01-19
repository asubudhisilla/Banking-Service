package com.assignment.banking.BankingService.controller;

import com.assignment.banking.BankingService.dto.request.MoneyTransferRequest;
import com.assignment.banking.BankingService.dto.response.MoneyTransferResponse;
import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.entity.Transactions;
import com.assignment.banking.BankingService.exception.BalanceMismatchException;
import com.assignment.banking.BankingService.service.AccountService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banking/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<String> createAccount(@Valid @RequestBody Account account) {
        logger.info("POST request for payload" + account.toString());
        if (!account.getClosingBalance().equals(account.getOpeningBalance()))
            throw new BalanceMismatchException();
        Account accountRes = accountService.createAccount(account);
        return ResponseEntity.ok().body(accountRes.getAccountNumber().toString());
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        logger.info("GET request to fetch balance details");
        return ResponseEntity.ok().body(accountService.getAllAccount());
    }


    @PutMapping("/transfer")
    public ResponseEntity<MoneyTransferResponse> transferMoney(@Valid @RequestBody MoneyTransferRequest moneyTransferRequest) {
        logger.info("PUT request for transfer" + moneyTransferRequest.toString());
        Account fromAccount = accountService.getAccountDetailsByAccountNumber(moneyTransferRequest.getFromAccount());
        Account toAccount = accountService.getAccountDetailsByAccountNumber(moneyTransferRequest.getToAccount());
        List<Transactions> pair = accountService.transfer(fromAccount, toAccount, moneyTransferRequest);
        return ResponseEntity.ok().body(convertAccountsToAccountTransferResponse(fromAccount, toAccount, pair));
    }



    private MoneyTransferResponse convertAccountsToAccountTransferResponse(Account fromAccount, Account toAccount, List<Transactions> pair) {
        MoneyTransferResponse moneyTransferResponse = MoneyTransferResponse.builder()
                .senderAccountNumber(fromAccount.getAccountNumber())
                .senderAccountBalance(fromAccount.getClosingBalance())
                .senderTransaction(pair.get(0))
                .senderFirstName(fromAccount.getName())
                .receiverAccountBalance(toAccount.getClosingBalance())
                .receiverAccountNumber(toAccount.getAccountNumber())
                .receiverTransaction(pair.get(1))
                .receiverFirstName(toAccount.getName())
                .build();
        return moneyTransferResponse;
    }
}
