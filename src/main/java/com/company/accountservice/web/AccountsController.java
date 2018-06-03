package com.company.accountservice.web;

import com.company.accountservice.model.Account;
import com.company.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountsController {

    private final AccountService accountService;

    @Autowired
    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(path = "/accounts", method = GET)
    public ResponseEntity<List<Account>> getAccounts(){
        return ResponseEntity.ok().body(accountService.getAccounts());
    }

    @RequestMapping(path = "/accounts", method = POST)
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountRequest createAccountRequest){
        Account account = accountFrom(createAccountRequest);

        Account createdAccount = accountService.createAccount(account);
        return created(URI.create("/accounts/" + createdAccount.getId())).body(createdAccount);
    }

    private Account accountFrom(@RequestBody CreateAccountRequest createAccountRequest) {
        return Account.builder()
                .firstName(createAccountRequest.getFirstName())
                .secondName(createAccountRequest.getSecondName())
                .accountNumber(createAccountRequest.getAccountNumber())
                .build();
    }
}
