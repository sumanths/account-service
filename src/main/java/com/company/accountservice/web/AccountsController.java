package com.company.accountservice.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Collections.emptyList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class AccountsController {

    @RequestMapping(path = "/accounts", method = GET)
    public ResponseEntity<?> getAccounts(){
        return ResponseEntity.ok().body(emptyList());
    }
}
