package com.company.accountservice.service;

import com.company.accountservice.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();

    Account createAccount(Account account);

    void deleteAccount(Integer accountId);
}
