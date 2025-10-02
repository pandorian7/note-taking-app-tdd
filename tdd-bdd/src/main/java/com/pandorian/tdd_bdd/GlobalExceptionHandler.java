package com.pandorian.tdd_bdd;

import com.pandorian.tdd_bdd.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, String>> handleCustom(ApplicationException e) {
        return ResponseEntity.status(e.getStatusCode())  // or a fixed code
                .body(Map.of("error", e.getMessage(), "code", e.getClass().getSimpleName()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        return ResponseEntity.status(500)
                .body(Map.of("error", "Internal Server Error", "code", "SERVER_ERROR"));
    }

    @ExceptionHandler(IncompleteUserSignupException.class)
    public ResponseEntity<Map<String, Object>> handleIncompleteUserSignup(IncompleteUserSignupException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "code", "INCOMPLETE_USER_SIGNUP"
                ));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResource(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "code", "RESOURCE_NOT_FOUND",
                        "path", ex.getResourcePath()
                ));
    }

    @ExceptionHandler(RequiredArgumentMissingException.class)
    public ResponseEntity<Map<String, Object>> handleRequiredArgumentMissing(RequiredArgumentMissingException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", ex.getMessage(),
                        "code", "REQUIRED_ARGUMENT_MISSING",
                        "field", ex.getPropertyName()
                ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(Map.of(
                        "error", "HTTP method not supported",
                        "code", "METHOD_NOT_ALLOWED"
                ));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(Map.of(
                        "error", "Media type not supported",
                        "code", "UNSUPPORTED_MEDIA_TYPE"
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleMalformedRequest(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Malformed or missing request body",
                        "code", "INVALID_BODY"
                ));
    }
}