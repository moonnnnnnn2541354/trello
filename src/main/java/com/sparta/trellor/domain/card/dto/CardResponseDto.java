package com.sparta.trellor.domain.card.dto;

import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.comment.dto.response.CommentResponseDto;
import com.sparta.trellor.domain.comment.entity.Comment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CardResponseDto {
    private Long id;
    private String cardTitle;
    private String cardInfo;
    private String cardColor;
    private String username;
    private List<CommentResponseDto> commentList;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.cardTitle = card.getCardTitle();
        this.cardInfo = card.getCardInfo();
        this.cardColor = card.getCardColor();
        this.username = card.getUser().getUsername();
    }

    public CardResponseDto(Card card, List<Comment> commentList) {
        this.id = card.getId();
        this.cardTitle = card.getCardTitle();
        this.cardInfo = card.getCardInfo();
        this.cardColor = card.getCardColor();
        this.commentList = new ArrayList<>();
        for (Comment comment : commentList) {
            this.commentList.add(new CommentResponseDto(comment));
        }
        this.username = card.getUser().getUsername();
    }

}
