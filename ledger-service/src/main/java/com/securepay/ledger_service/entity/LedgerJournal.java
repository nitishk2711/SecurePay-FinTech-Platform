package com.securepay.ledger_service.entity;

import com.securepay.ledger_service.enums.JournalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
    name = "ledger_journals",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_ledger_journal_transaction",
            columnNames = "transaction_id"
        ),
        @UniqueConstraint(
            name = "uk_ledger_journal_idempotency",
            columnNames = "idempotency_key"
        )
    },
    indexes = {
        @Index(
            name = "idx_journal_created_at",
            columnList = "created_at"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class LedgerJournal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
        name = "transaction_id",
        nullable = false,
        updatable = false,
        length = 100
    )
    private String transactionId;

    @Column(
        name = "idempotency_key",
        nullable = false,
        updatable = false,
        length = 150
    )
    private String idempotencyKey;

    @Column(nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private JournalStatus status;

    @Column(length = 500)
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @OneToMany(
        mappedBy = "journal",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<LedgerEntry> entries = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    public void addEntry(LedgerEntry entry) {
        entries.add(entry);
        entry.setJournal(this);
    }
}