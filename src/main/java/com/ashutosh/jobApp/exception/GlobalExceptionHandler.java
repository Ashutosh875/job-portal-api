package com.ashutosh.jobApp.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(e.getMessage() , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex){
        return new ResponseEntity<>("Internal error occurred" , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
