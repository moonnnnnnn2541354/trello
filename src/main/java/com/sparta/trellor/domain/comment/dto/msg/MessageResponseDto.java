package com.sparta.trellor.domain.comment.dto.msg;

import com.sparta.trellor.domain.comment.dto.response.CommentResponseDto;
import com.sparta.trellor.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MessageResponseDto {

    private String msg;
    private int status;
    private CommentResponseDto data;

    @Builder
    public MessageResponseDto(String msg, int status, Comment comment) {
        this.msg = msg;
        this.status = status;
        this.data = new CommentResponseDto(comment);
    }
}
