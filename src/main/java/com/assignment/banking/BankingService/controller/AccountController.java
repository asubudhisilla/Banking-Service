package com.assignment.banking.BankingService.controller;

import com.assignment.banking.BankingService.dto.request.MoneyTransferRequest;
import com.assignment.banking.BankingService.dto.request.MoneyWithdrawRequest;
import com.assignment.banking.BankingService.dto.response.AccountBalanceResponse;
import com.assignment.banking.BankingService.dto.response.MoneyTransferResponse;
import com.assignment.banking.BankingService.dto.response.MoneyWithdrawResponse;
import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.entity.Transactions;
import com.assignment.banking.BankingService.exception.BalanceMismatchException;
import com.assignment.banking.BankingService.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/banking/account")
public class AccountController {

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IAccountService accountService;

    @PostMapping
    public ResponseEntity<String> saveAccount(@Valid @RequestBody Account account) {
        logger.info("POST request for payload" + account.toString());
        if (!account.getClosingBalance().equals(account.getOpeningBalance()))
            throw new BalanceMismatchException();
        Account accountRes = accountService.createAccount(account);
        return ResponseEntity.ok().body(accountRes.getAccountNumber().toString());
    }

    @GetMapping
    public ResponseEntity<List<AccountBalanceResponse>> getAllAccountBalanceDetails() {
        logger.info("GET request to fetch balance details");
        return ResponseEntity.ok().body(accountService.getAllAccount().stream().map(this::convertAccountToAccountBalanceResponse).collect(Collectors.toList()));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<MoneyWithdrawResponse> withdrawMoney(@Valid @RequestBody MoneyWithdrawRequest moneyWithdrawRequest) {
        logger.info("PUT request for withdraw" + moneyWithdrawRequest.toString());
        Account account = accountService.getAccountDetailsByAccountNumber(moneyWithdrawRequest.getAccountNumber());
        Transactions transaction = accountService.withdraw(account, moneyWithdrawRequest);
        return ResponseEntity.ok().body(convertAccountToAccountWithdrawResponse(account, transaction));
    }

    @PutMapping("/transfer")
    public ResponseEntity<MoneyTransferResponse> transferMoney(@Valid @RequestBody MoneyTransferRequest moneyTransferRequest) {
        logger.info("PUT request for transfer" + moneyTransferRequest.toString());
        Account fromAccount = accountService.getAccountDetailsByAccountNumber(moneyTransferRequest.getFromAccount());
        Account toAccount = accountService.getAccountDetailsByAccountNumber(moneyTransferRequest.getToAccount());
        List<Transactions> pair = accountService.transfer(fromAccount, toAccount, moneyTransferRequest);
        return ResponseEntity.ok().body(convertAccountsToAccountTransferResponse(fromAccount, toAccount, pair));
    }

    private AccountBalanceResponse convertAccountToAccountBalanceResponse(Account account) {
        AccountBalanceResponse accountBalanceResponse = mapper.map(account, AccountBalanceResponse.class);
        accountBalanceResponse.setAccountNumber(account.getAccountNumber());
        accountBalanceResponse.setClosingBalance(account.getClosingBalance());
        accountBalanceResponse.setOpeningBalance(account.getOpeningBalance());
        accountBalanceResponse.setFirstName(account.getFirstName());
        accountBalanceResponse.setLastName(account.getLastName());
        return accountBalanceResponse;
    }

    private MoneyWithdrawResponse convertAccountToAccountWithdrawResponse(Account account, Transactions transaction) {
        MoneyWithdrawResponse moneyWithdrawResponse = mapper.map(account, MoneyWithdrawResponse.class);
        moneyWithdrawResponse.setAccountNumber(account.getAccountNumber());
        moneyWithdrawResponse.setStatus(transaction.getStatus().toString());
        moneyWithdrawResponse.setTransactionNumber(transaction.getTransactionId());
        moneyWithdrawResponse.setCurrentBalance(account.getClosingBalance());
        moneyWithdrawResponse.setFirstName(account.getFirstName());
        return moneyWithdrawResponse;
    }

    private MoneyTransferResponse convertAccountsToAccountTransferResponse(Account fromAccount, Account toAccount, List<Transactions> pair) {
        MoneyTransferResponse moneyTransferResponse = MoneyTransferResponse.builder()
                .senderAccountNumber(fromAccount.getAccountNumber())
                .senderAccountBalance(fromAccount.getClosingBalance())
                .senderTransaction(pair.get(0))
                .senderFirstName(fromAccount.getFirstName())
                .receiverAccountBalance(toAccount.getClosingBalance())
                .receiverAccountNumber(toAccount.getAccountNumber())
                .receiverTransaction(pair.get(1))
                .receiverFirstName(toAccount.getFirstName())
                .build();
        return moneyTransferResponse;
    }
}
