package com.sparta.trellor.domain.comment.exception;

import com.sparta.trellor.global.exception.error.ErrorCode;
import com.sparta.trellor.global.exception.error.RestApiException;

public class CommentExistsException extends RestApiException {
    public CommentExistsException(ErrorCode errorCode) {
        super(errorCode);
    }

}
