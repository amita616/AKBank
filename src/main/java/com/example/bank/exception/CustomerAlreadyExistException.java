package com.example.bank.exception;

public class CustomerAlreadyExistException extends RuntimeException{

    public CustomerAlreadyExistException(String message){
        super(message);
    }
}
