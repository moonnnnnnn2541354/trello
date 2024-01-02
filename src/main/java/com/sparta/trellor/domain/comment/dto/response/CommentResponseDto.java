package com.sparta.trellor.domain.comment.dto.response;

import com.sparta.trellor.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String username;
    private String info;
    private LocalDateTime date;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.info = comment.getComment_info();
        this.date = comment.getCreatedAt();
    }

}
