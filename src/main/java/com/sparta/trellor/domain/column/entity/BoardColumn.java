package com.sparta.trellor.domain.column.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.column.dto.BoardColumnRequestDto;
import com.sparta.trellor.domain.utils.BaseTime;
import jakarta.persistence.*;
import jakarta.persistence.Id;
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


    public BoardColumn(BoardColumnRequestDto columnRequestDto, Board board) {
        this.columnName = columnRequestDto.getColumnName();
        this.board = board;
    }

    /////////////////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board ;

    // 정적 변수를 사용하여 1씩 증가하는 값을 유지
    private static int counter = 1;

    @PrePersist
    public void prePersist() {
        if (columnNo == null) {
            // 여기에서 커스텀 로직으로 columnNo를 생성하도록 처리
            // 예: columnNo를 현재 시간 기반으로 생성하거나, 랜덤한 값을 할당하거나, 특정 규칙을 따라 값을 생성하는 등의 방법이 가능
            counter = this.board.getBoardColumns().size();
            columnNo = generateCustomColumnNo();
        }
    }

    // 커스텀 로직을 구현하는 메서드
    private Long generateCustomColumnNo() {
        // 1씩 증가하는 값을 반환
        return (long) counter++;
    }
    /////////////////////////////////////////////////////////////////

    public void update(Long columnNo) {
        this.columnNo = columnNo;
    }

    public void updateName(String columnName) {
        this.columnName = columnName;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }
}
