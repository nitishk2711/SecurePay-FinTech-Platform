package com.securepay.transaction_service.exception;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
            TransactionNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                error("TRANSACTION_NOT_FOUND", ex.getMessage())
        );
    }

    @ExceptionHandler(InvalidTransactionStateException.class)
    public ResponseEntity<?> handleInvalidState(
            InvalidTransactionStateException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                error("INVALID_TRANSACTION_STATE", ex.getMessage())
        );
    }

    @ExceptionHandler(DuplicateTransactionException.class)
    public ResponseEntity<?> handleDuplicate(
            DuplicateTransactionException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                error("DUPLICATE_TRANSACTION", ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(
                error("VALIDATION_ERROR", "Request validation failed")
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleBadRequest(
            IllegalArgumentException ex) {

        return ResponseEntity.badRequest().body(
                error("BAD_REQUEST", ex.getMessage())
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(
            DataIntegrityViolationException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                error("DATA_CONFLICT",
                        "A conflicting transaction record exists")
        );
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<?> handleConcurrentUpdate(
            OptimisticLockingFailureException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                error("CONCURRENT_UPDATE",
                        "The transaction changed concurrently; reload it")
        );
    }

    private Map<String, Object> error(String code, String message) {
        return Map.of(
                "timestamp", Instant.now().toString(),
                "code", code,
                "message", message
        );
    }
}
