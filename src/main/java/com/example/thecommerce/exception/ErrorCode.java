package com.example.thecommerce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, ""),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "");

    private final HttpStatus httpStatus;
    private final String message;
}