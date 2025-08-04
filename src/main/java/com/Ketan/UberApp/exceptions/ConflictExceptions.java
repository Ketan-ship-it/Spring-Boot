package com.Ketan.UberApp.exceptions;

public class ConflictExceptions extends RuntimeException{
    public ConflictExceptions() {
    }

    public ConflictExceptions(String message) {
        super(message);
    }
}
