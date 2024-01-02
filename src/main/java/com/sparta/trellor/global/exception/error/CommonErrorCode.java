package com.sparta.trellor.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "입력이 잘못 되었습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요소를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 문제가 발생하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
