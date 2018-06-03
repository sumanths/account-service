package com.company.accountservice.builders;

import com.company.accountservice.model.Account;

import static com.company.accountservice.utils.IntegerUtils.randomInteger;
import static com.company.accountservice.utils.StringUtils.randomString;

public class AccountBuilder {

    public static Account account() {
        return Account.builder()
                .id(randomInteger())
                .firstName(randomString())
                .secondName(randomString())
                .accountNumber(randomString())
                .build();
    }
}
