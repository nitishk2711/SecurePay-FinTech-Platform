package com.securepay.account_service.dto;

import com.securepay.account_service.enums.AccountStatus;
import com.securepay.account_service.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String accountNumber;
    private String bankName;
    private String branchName;
    private Double balance;
    private AccountType accountType;
}
