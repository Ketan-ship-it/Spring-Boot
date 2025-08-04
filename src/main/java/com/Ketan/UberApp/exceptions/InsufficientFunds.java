package com.Ketan.UberApp.exceptions;

public class InsufficientFunds extends RuntimeException{
    public InsufficientFunds() {
    }

    public InsufficientFunds(String message) {
        super(message);
    }
}
