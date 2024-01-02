package com.sparta.trellor.domain.card.dto.msg;

import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.entity.Card;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CardMessageResponseDto {

    private String msg;
    private int status;
    private CardResponseDto data;

    @Builder
    public CardMessageResponseDto(String msg, int status, Card card) {
        this.msg = msg;
        this.status = status;
        this.data = new CardResponseDto(card);
    }
}
