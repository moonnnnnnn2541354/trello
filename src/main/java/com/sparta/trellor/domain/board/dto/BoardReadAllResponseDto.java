package com.sparta.trellor.domain.board.dto;


import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.entity.UserBoard;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardReadAllResponseDto {

    private String boardName;
    private String boardColor;
    private String boardInfo;
    private List<UserBoard> userBoard;
    private List<BoardColumn> boardColumns;
    private Long boardId;

    public BoardReadAllResponseDto(Board board) {
        this.boardId = board.getBoardId();
        this.boardName = board.getBoardName();
        this.boardColor = board.getBoardColor();
        this.boardInfo = board.getBoardInfo();
        this.userBoard = board.getUserBoards();
        this.boardColumns = board.getBoardColumns().stream()
            .map(BoardFindColumnResponseDto::new)
            .collect(Collectors.toList());
    }
}
