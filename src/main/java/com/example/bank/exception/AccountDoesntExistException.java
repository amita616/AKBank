package com.example.bank.exception;

public class AccountDoesntExistException extends RuntimeException{
    public AccountDoesntExistException(String message){
        super(message);
    }
}
