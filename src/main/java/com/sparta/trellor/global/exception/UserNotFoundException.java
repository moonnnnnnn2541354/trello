package com.sparta.trellor.global.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
