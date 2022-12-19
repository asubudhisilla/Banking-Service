package com.assignment.banking.BankingService.controller;

import com.assignment.banking.BankingService.configuration.AccountConfiguration;
import com.assignment.banking.BankingService.entity.Account;
import com.assignment.banking.BankingService.entity.CardDetails;
import com.assignment.banking.BankingService.entity.CardType;
import com.assignment.banking.BankingService.service.AccountServiceImpl;
import com.assignment.banking.BankingService.service.TransactionsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({AccountConfiguration.class})
@ActiveProfiles("test")
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private TransactionsServiceImpl transactionsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenAccountObject_whenCreateAccount_thenReturnSavedAccount() throws Exception {

        // given - precondition or setup
        CardDetails cardDetails = CardDetails.builder()
                .cardNumber(UUID.randomUUID())
                .type(CardType.CREDIT)
                .build();
        Account account = Account.builder()
                .accountNumber(UUID.randomUUID())
                .dob(LocalDate.of(1990, 6, 4))
                .firstName("Amar")
                .lastName("Silla")
                .address("NL")
                .openingBalance(new BigDecimal(100.00))
                .closingBalance(new BigDecimal(100.00))
                .cardDetails(cardDetails)
                .build();
        given(accountService.createAccount(any(Account.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/banking/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(account)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isOk());
    }
}
