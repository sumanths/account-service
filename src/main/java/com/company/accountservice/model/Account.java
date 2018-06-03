package com.company.accountservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
public class Account {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstName;
    private String secondName;
    private String accountNumber;

}
