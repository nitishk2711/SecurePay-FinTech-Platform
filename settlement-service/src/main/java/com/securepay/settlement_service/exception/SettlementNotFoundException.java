
package com.securepay.settlement_service.exception;

public class SettlementNotFoundException
        extends RuntimeException {

    public SettlementNotFoundException(String message) {
        super(message);
    }
}
