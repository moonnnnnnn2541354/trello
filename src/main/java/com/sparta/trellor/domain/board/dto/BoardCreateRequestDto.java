package com.sparta.trellor.domain.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BoardCreateRequestDto {

    private String boardName;
    private String boardColor;
    private String boardInfo;

}
