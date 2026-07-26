package com.securepay.ledger_service.service;

import com.securepay.ledger_service.entity.LedgerEntry;
import com.securepay.ledger_service.request.LedgerRequest;
import com.securepay.ledger_service.response.LedgerResponse;

import java.util.List;

public interface LedgerService {

    LedgerResponse createLedger(LedgerRequest request);

    List<LedgerEntry> getHistory(String transactionId);

}
