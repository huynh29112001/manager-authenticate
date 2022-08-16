package com.example.demo.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException() {
        super("Data not found");
    }
    public DataNotFoundException(String exeption) {
        super(exeption);
    }
}
