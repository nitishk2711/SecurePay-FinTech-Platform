package com.securepay.auth_service.exception;

import com.securepay.auth_service.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(
            MethodArgumentNotValidException ex) {

        return error(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                "One or more request fields are invalid");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            IllegalArgumentException ex) {

        // Do not return raw exception details to clients.
        return error(
                HttpStatus.BAD_REQUEST,
                "INVALID_REQUEST",
                "The request could not be completed");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(
            IllegalStateException ex) {

        return error(
                HttpStatus.CONFLICT,
                "OPERATION_NOT_ALLOWED",
                "The operation could not be completed");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(
            Exception ex) {

        // Log the exception internally with a correlation ID.
        return error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_ERROR",
                "An unexpected error occurred");
    }

    private ResponseEntity<ApiError> error(
            HttpStatus status,
            String code,
            String message) {

        return ResponseEntity.status(status)
                .body(new ApiError(
                        Instant.now(),
                        status.value(),
                        code,
                        message));
    }
}
