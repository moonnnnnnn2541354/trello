package com.sparta.trellor.global.exception;

public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException(String msg){
        super(msg);
    }
}
