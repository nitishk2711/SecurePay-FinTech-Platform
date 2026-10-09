
package com.securepay.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleNotFound(
            OrderNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "timestamp", Instant.now().toString(),
                    "status", 404,
                    "code", "ORDER_NOT_FOUND",
                    "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(InvalidOrderStateException.class)
    public ResponseEntity<?> handleInvalidState(
            InvalidOrderStateException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                    "timestamp", Instant.now().toString(),
                    "status", 409,
                    "code", "INVALID_ORDER_STATE",
                    "message", ex.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": "
                        + error.getDefaultMessage())
                .orElse("Invalid request");

        return ResponseEntity.badRequest()
                .body(Map.of(
                    "timestamp", Instant.now().toString(),
                    "status", 400,
                    "code", "VALIDATION_ERROR",
                    "message", message
                ));
    }
}
