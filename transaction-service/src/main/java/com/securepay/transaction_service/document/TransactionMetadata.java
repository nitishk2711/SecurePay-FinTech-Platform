package com.securepay.transaction_service.document;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "transaction_metadata")
@Getter
@Setter
public class TransactionMetadata {

    @Id
    private String id;

    private String transactionId;

    private String device;

    private String ipAddress;

    private String location;

    private String channel;

}
