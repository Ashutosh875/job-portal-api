package com.ashutosh.jobApp.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }
}
