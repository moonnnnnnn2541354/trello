package com.sparta.trellor.global.exception;

import org.springframework.web.filter.OncePerRequestFilter;

public class FormNotMatchException extends RuntimeException{
    public FormNotMatchException(String msg){
        super(msg);
    }

}