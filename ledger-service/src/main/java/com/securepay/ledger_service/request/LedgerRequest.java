package com.securepay.ledger_service.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LedgerRequest {

    private String transactionId;

    private String debitAccount;

    private String creditAccount;

    private BigDecimal amount;

    private String description;

}
