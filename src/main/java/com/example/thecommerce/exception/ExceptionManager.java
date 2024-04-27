package com.example.thecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode().name() + " " + e.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> validationExceptionHandler(ConstraintViolationException e) {

        String message = "";

        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            message += " " + constraintViolation.getMessage();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("USER_BAD_REQUEST " + message);
    }
}
