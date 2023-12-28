package com.sparta.trellor.domain.column.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sparta.trellor.domain.column.dto.BoardColumnRequestDto;
import com.sparta.trellor.domain.utils.BaseTime;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import java.util.ArrayList;
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


    public BoardColumn(BoardColumnRequestDto columnRequestDto) {
        this.columnName = columnRequestDto.getColumnName();
    }

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
