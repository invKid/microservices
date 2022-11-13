package com.agileactors.account.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public record RestApiException(String message, HttpStatus httpStatus, LocalDateTime timestamp){
}
