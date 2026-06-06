package com.ecommerce.amazon.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) 
                .body(Map.of(
                        "erro", ex.getMessage(),
                        "status", 400,
                        "timestamp", LocalDateTime.now()
                ));
    }

    // Trata qualquer outro erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) 
                .body(Map.of(
                        "erro", "Erro interno no servidor",
                        "status", 500,
                        "timestamp", LocalDateTime.now()
                ));
    }
}