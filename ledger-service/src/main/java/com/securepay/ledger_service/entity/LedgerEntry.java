package com.securepay.ledger_service.entity;


import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name = "ledger_entries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;

    private String accountId;

    private String entryType;

    private BigDecimal amount;

    private String description;

}
