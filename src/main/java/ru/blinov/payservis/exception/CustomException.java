package ru.blinov.payservis.exception;

public class CustomException extends RuntimeException {
    private String msg;

    public CustomException(String msg){
        super(msg);
    }
}
