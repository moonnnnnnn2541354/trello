package com.sparta.trellor.domain.comment.dto.msg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MessageDeleteDto {

    private String msg;
    private int status;

    @Builder
    public MessageDeleteDto(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}
