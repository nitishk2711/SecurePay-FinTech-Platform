package com.securepay.ledger_service.service.serviceImpl;

import com.securepay.ledger_service.entity.LedgerEntry;
import com.securepay.ledger_service.enums.EntryType;
import com.securepay.ledger_service.repository.LedgerRepository;
import com.securepay.ledger_service.request.LedgerRequest;
import com.securepay.ledger_service.response.LedgerResponse;
import com.securepay.ledger_service.service.LedgerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LedgerServiceImpl implements LedgerService {

    private final LedgerRepository repository;

    @Override
    public LedgerResponse createLedger(LedgerRequest request) {
        LedgerEntry debit = new LedgerEntry();
        debit.setTransactionId(request.getTransactionId());
        debit.setAccountId(request.getDebitAccount());
        debit.setEntryType(EntryType.DEBIT.name());
        debit.setAmount(request.getAmount());
        debit.setDescription(request.getDescription());
        LedgerEntry credit = new LedgerEntry();
        credit.setTransactionId(request.getTransactionId());
        credit.setAccountId(request.getCreditAccount());
        credit.setEntryType(EntryType.CREDIT.name());
        credit.setAmount(request.getAmount());
        credit.setDescription(request.getDescription());
        repository.save(debit);
        repository.save(credit);
        validateBalance(request.getTransactionId());

        return new LedgerResponse(
                request.getTransactionId(),
                "SUCCESS"
        );

    }

    @Override
    public List<LedgerEntry> getHistory(String transactionId) {
        return List.of();
    }


    private void validateBalance(String transactionId) {

        List<LedgerEntry> entries = repository.findByTransactionId(transactionId);
        BigDecimal debit = entries.stream()
                .filter(e -> e.getEntryType().equals("DEBIT"))
                .map(LedgerEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal credit = entries.stream()
                .filter(e -> e.getEntryType().equals("CREDIT"))
                .map(LedgerEntry::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (!debit.equals(credit)) {
            throw new RuntimeException("Ledger imbalance detected");
        }
    }
}
