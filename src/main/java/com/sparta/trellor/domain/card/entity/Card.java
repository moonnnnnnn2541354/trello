package com.sparta.trellor.domain.card.entity;

import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.utils.BaseTime;
import com.sparta.trellor.global.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity(name = "Cards")
@NoArgsConstructor
public class Card extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cardTitle;
    @Column
    private String cardInfo;
    @Column
    private String cardColor;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

    public Card(CardRequestDto cardRequestDto, UserDetailsImpl userDetails) {
        this.cardTitle = cardRequestDto.getCardTitle();
        this.cardInfo = cardRequestDto.getCardInfo();
        this.cardColor = cardRequestDto.getCardColor();
        this.user = userDetails.getUser();
    }

    public void setCardTitle(String cardTitle) {
        this.cardTitle = cardTitle;
    }

    public void setCardInfo(String cardInfo) {
        this.cardInfo = cardInfo;
    }

    public void setCardColor(String cardColor) {
        this.cardColor = cardColor;
    }
}
