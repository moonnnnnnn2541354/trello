package com.sparta.trellor.domain.card.dto;

import com.sparta.trellor.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CardResponseDto {
    private Long id;
    private String cardTitle;
    private String cardInfo;
    private String cardColor;
    private String username;
    private LocalDateTime createdAt;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.cardTitle = card.getCardTitle();
        this.cardInfo = card.getCardInfo();
        this.cardColor = card.getCardColor();
        this.username = card.getUser().getUsername();
        this.createdAt = card.getCreatedAt();
    }

}
