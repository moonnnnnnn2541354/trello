package com.sparta.trellor.domain.card.dto;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import lombok.Getter;

@Getter
public class CardRequestDto {

    private Long id;
    private String cardTitle;
    private String cardInfo;
    private String cardColor;

}
