package com.sparta.trellor.domain.card.exception;

import com.sparta.trellor.global.exception.error.ErrorCode;
import com.sparta.trellor.global.exception.error.RestApiException;

public class CardExistsException extends RestApiException {
    public CardExistsException(ErrorCode errorCode) {
        super(errorCode);
    }

}
