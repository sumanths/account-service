package com.company.accountservice.service;

import com.company.accountservice.model.Account;
import com.company.accountservice.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static com.company.accountservice.builders.AccountBuilder.account;
import static com.company.accountservice.builders.AccountBuilder.accountWithoutId;
import static com.company.accountservice.utils.IntegerUtils.randomInteger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    @Before
    public void setup(){
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void shouldReturnAccounts(){
        Account account1 = account();
        Account account2 = account();
        List<Account> accounts = Arrays.asList(account1, account2);
        given(accountRepository.findAll()).willReturn(accounts);

        List<Account> accountsList = accountService.getAccounts();

        assertThat(accountsList).isEqualTo(accounts);
    }

    @Test
    public void shouldCreateANewAccount(){
        Account account = accountWithoutId();
        Account accountWithId = account();

        given(accountRepository.save(account)).willReturn(accountWithId);

        Account createdAccount = accountService.createAccount(account);

        assertThat(createdAccount).isEqualTo(accountWithId);
    }

    @Test
    public void shouldDeleteAnAccount(){
        Integer accountId = randomInteger();

        accountService.deleteAccount(accountId);

        verify(accountRepository).deleteById(accountId);
    }
}