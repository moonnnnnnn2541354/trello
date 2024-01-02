package com.sparta.trellor.domain.board.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardInviteRequestDto {
    private Long boardId;
    private String userName;
}
