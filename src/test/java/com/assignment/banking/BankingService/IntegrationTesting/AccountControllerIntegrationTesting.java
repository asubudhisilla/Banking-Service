package com.assignment.banking.BankingService.IntegrationTesting;

import com.assignment.banking.BankingService.BankingServiceApplication;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankingServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AccountControllerIntegrationTesting {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateAccount() throws Exception {

        String accountInfo = "{\n" +
                "    \"firstName\" : \"Amar\",\n" +
                "    \"lastName\" : \"s\",\n" +
                "    \"dob\" : \"1990-04-06\",\n" +
                "    \"address\" : \"Pune\",\n" +
                "    \"openingBalance\" : 100.00,\n" +
                "    \"closingBalance\" : 100.00,\n" +
                "    \"cardDetails\" : {\n" +
                "        \"type\" : \"DEBIT\"\n" +
                "    }\n" +
                "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/banking/account")
                .accept(MediaType.APPLICATION_JSON).content(accountInfo)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        Assert.assertEquals("http response status is wrong", 200, status);
    }

    @Test
    public void testGetAllAccount() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/banking/account")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
