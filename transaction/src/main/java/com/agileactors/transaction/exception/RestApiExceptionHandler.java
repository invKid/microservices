package com.agileactors.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler(value = {RestApiRequestException.class})
    public ResponseEntity<Object> handlerApiRequestException(RestApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        RestApiException apiException = new RestApiException(
                e.getMessage(),
                badRequest,
                LocalDateTime.now());

        return new ResponseEntity<>(apiException, badRequest);
    }




}
