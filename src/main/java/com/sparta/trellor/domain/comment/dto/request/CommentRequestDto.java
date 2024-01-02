package com.sparta.trellor.domain.comment.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    @Pattern(regexp = "^.+$", message = "문자를 입력해주세요")
    private String info;

}
