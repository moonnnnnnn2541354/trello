package com.sparta.trellor.domain.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.trellor.domain.board.dto.BoardCreateRequestDto;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.utils.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "board")
@NoArgsConstructor
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_name", nullable = false)
    private String boardName;

    @Column(name = "board_color", nullable = false)
    private String boardColor;

    @Column(name = "board_info", nullable = false)
    private String boardInfo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private List<UserBoard> userBoards = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    List<BoardColumn> boardColumns = new ArrayList<>();

    public Board(BoardCreateRequestDto requestDto, User user) {
        this.boardName = requestDto.getBoardName();
        this.boardColor = requestDto.getBoardColor();
        this.boardInfo = requestDto.getBoardInfo();
        user.addBoardList(this);
    }

    public void addUserBoardList(UserBoard userBoard) {
        this.userBoards.add(userBoard);
    }

    public void addUserBoardList(BoardColumn boardColumn) {
        this.boardColumns.add(boardColumn);
    }

    public void update(BoardCreateRequestDto requestDto) {
        this.boardName = requestDto.getBoardName();
        this.boardColor = requestDto.getBoardColor();
        this.boardInfo = requestDto.getBoardInfo();
    }
}
