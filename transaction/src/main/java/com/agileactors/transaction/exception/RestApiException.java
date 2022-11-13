package com.agileactors.transaction.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public record RestApiException(String message, HttpStatus httpStatus, LocalDateTime timestamp){
}
