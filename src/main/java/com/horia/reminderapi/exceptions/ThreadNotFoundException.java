package com.horia.reminderapi.exceptions;

public class ThreadNotFoundException extends RuntimeException {

    public ThreadNotFoundException(String message) {
        super(message);
    }

    public ThreadNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
