package com.sparta.trellor.global.exception.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();
    HttpStatus getHttpStatus();
    String getMessage();
}
