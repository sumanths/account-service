package com.company.accountservice.web;

import com.company.accountservice.model.Account;
import com.company.accountservice.service.AccountService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.company.accountservice.builders.AccountBuilder.account;
import static com.company.accountservice.utils.IntegerUtils.randomInteger;
import static com.company.accountservice.utils.StringUtils.randomString;
import static java.util.Collections.emptyList;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AccountsController.class)
public class AccountsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private Gson gson = new GsonBuilder().create();

    @Before
    public void setUp() {
        given(accountService.getAccounts()).willReturn(emptyList());
    }

    @Test
    public void shouldReturnZeroAccountsWhenThereAreNone() throws Exception {
        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void shouldReturnExistingAccounts() throws Exception {
        Account account1 = account();
        Account account2 = account();
        List<Account> accounts = Arrays.asList(account1, account2);
        given(accountService.getAccounts()).willReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(accounts)));
    }

    @Test
    public void shouldCreateAnAccount() throws Exception {
        CreateAccountRequest request = CreateAccountRequest.builder()
                .firstName(randomString())
                .secondName(randomString())
                .accountNumber(randomString())
                .build();

        Account account = Account.builder()
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .accountNumber(request.getAccountNumber())
                .build();

        Account accountWithId = Account.builder()
                .id(randomInteger())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .accountNumber(request.getAccountNumber())
                .build();

        given(accountService.createAccount(account)).willReturn(accountWithId);

        mockMvc.perform(post("/accounts")
                .content(gson.toJson(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/accounts/" + accountWithId.getId()));
    }
}