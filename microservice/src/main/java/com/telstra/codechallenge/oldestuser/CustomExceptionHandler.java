package com.telstra.codechallenge.oldestuser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CustomException.class)
    private ResponseEntity<Object> handleCustomException(CustomException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getStatus(), ex.getResponseCode());
        return new ResponseEntity<>(response,
                response.getStatus());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    private ResponseEntity<Object> handleGeneralException(Exception ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(response,
                response.getStatus());
    }
}
