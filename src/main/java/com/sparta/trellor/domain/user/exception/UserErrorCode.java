package com.sparta.trellor.domain.user.exception;

import com.sparta.trellor.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    NOT_EXISTS_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "해당 계정에 대한 권한을 가지고 있지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
