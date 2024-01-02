package com.sparta.trellor.domain.comment.exception;

import com.sparta.trellor.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    NOT_EXISTS_CARD(HttpStatus.NOT_FOUND, "카드가 존재하지 않습니다."),
    NOT_EXISTS_COMMENT(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}
