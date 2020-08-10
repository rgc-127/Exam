package com.zzxx.exam.service;

public class IdOrPasswordException extends Exception{
    public IdOrPasswordException() {
    }

    public IdOrPasswordException(String message) {
        super(message);
    }

    public IdOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdOrPasswordException(Throwable cause) {
        super(cause);
    }

    public IdOrPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
