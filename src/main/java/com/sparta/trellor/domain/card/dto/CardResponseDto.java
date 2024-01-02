package com.sparta.trellor.domain.card.dto;

import com.sparta.trellor.domain.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

//    public CardResponseDto(Card card, List<Comment> commentList) {
//        this.id = card.getId();
//        this.cardTitle = card.getCardTitle();
//        this.cardInfo = card.getCardInfo();
//        this.cardColor = card.getCardColor();
//        this.commentList = new ArrayList<>();
//        for (Comment comment : commentList) {
//            this.commentList.add(new CommentResponse(comment));
//        }
//        this.username = card.getUser().getUsername();
//        this.createdAt = card.getCreatedAt();
//    }

}
