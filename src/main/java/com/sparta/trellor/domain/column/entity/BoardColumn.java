package com.sparta.trellor.domain.column.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.column.dto.BoardColumnRequestDto;
import com.sparta.trellor.domain.utils.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BoardColumn extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long columnId;

    @Column(name = "column_name", nullable = false)
    private String columnName;

    @Column(name = "column_no")
    private Long columnNo;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    @JsonBackReference
    private List<Card> cards = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;


    public BoardColumn(BoardColumnRequestDto columnRequestDto, Board board) {
        this.columnName = columnRequestDto.getColumnName();
        this.board = board;
    }

    private static int counter = 1;

    @PrePersist
    public void prePersist() {
        if (columnNo == null) {
            counter = this.board.getBoardColumns().size();
            columnNo = generateCustomColumnNo();
        }
    }

    private Long generateCustomColumnNo() {
        return (long) counter++;
    }

    public void update(Long columnNo) {
        this.columnNo = columnNo;
    }

    public void updateName(String columnName) {
        this.columnName = columnName;
    }


    public void addCard(Card card) {
        cards.add(card);
        card.setBoardColumn(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.setBoardColumn(null);
    }

    public void moveCardPosition(Card card, int newPosition) {
        if (cards.contains(card)) {
            cards.remove(card);
            cards.add(newPosition, card);
        }
    }
}
