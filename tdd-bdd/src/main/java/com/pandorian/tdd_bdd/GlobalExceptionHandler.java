package com.pandorian.tdd_bdd;

import com.pandorian.tdd_bdd.exceptions.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}