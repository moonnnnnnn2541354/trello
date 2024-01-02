package com.sparta.trellor.global.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String msg) {
        super(msg);
    }
}