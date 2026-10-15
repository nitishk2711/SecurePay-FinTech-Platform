
package com.securepay.settlement_service.exception;

public class InvalidSettlementStateException
        extends RuntimeException {

    public InvalidSettlementStateException(String message) {
        super(message);
    }
}
