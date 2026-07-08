package com.securepay.account_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.securepay.account_service.enums.AccountStatus;
import com.securepay.account_service.enums.AccountType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "accounts")
public class Account {

    @Id
    @JsonProperty("id")
    private String id;

    @Indexed(unique = true)
    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("branch_name")
    private String branchName;

    @JsonProperty("amount")
    private Double amount;

    @JsonProperty("account_type")
    private AccountType accountType;

    @JsonProperty("account_status")
    private AccountStatus accountStatus;

    @JsonProperty("balance")
    private Double balance;
}