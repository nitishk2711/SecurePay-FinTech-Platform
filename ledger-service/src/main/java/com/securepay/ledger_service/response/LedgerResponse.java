package com.securepay.ledger_service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LedgerResponse {

    private String transactionId;

    private String status;

}
