package com.sparta.trellor.domain.user.exception;

import com.sparta.trellor.global.exception.error.ErrorCode;
import com.sparta.trellor.global.exception.error.RestApiException;

public class UserExistsException extends RestApiException {
    public UserExistsException(ErrorCode errorCode) { super(errorCode); }
}
