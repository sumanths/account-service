package com.company.accountservice.web;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {

    private String firstName;
    private String secondName;
    private String accountNumber;
}
