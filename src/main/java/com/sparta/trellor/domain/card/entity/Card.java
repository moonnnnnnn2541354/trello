package com.sparta.trellor.domain.card.entity;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.utils.BaseTime;
import com.sparta.trellor.global.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "Cards")
@NoArgsConstructor
public class Card extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_title", nullable = false)
    private String cardTitle;

    @Column(name = "card_info", nullable = false)
    private String cardInfo;
    @Column(name = "card_color", nullable = false)
    private String cardColor;
    @Column(name = "card_no", nullable = false)
    private int cardNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private BoardColumn boardColumn;


    public Card(CardRequestDto cardRequestDto, UserDetailsImpl userDetails, Board board, BoardColumn column) {
        this.cardTitle = cardRequestDto.getCardTitle();
        this.cardInfo = cardRequestDto.getCardInfo();
        this.cardColor = cardRequestDto.getCardColor();
        this.user = userDetails.getUser();
        this.board = board;
        this.boardColumn = column;
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

    public void setBoardColumn(BoardColumn newColumn) {
    }
}
