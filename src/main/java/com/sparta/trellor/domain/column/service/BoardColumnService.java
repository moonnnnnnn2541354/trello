package com.sparta.trellor.domain.column.service;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.repository.BoardRepository;
import com.sparta.trellor.domain.column.dto.*;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import com.sparta.trellor.domain.column.repository.BoardColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardColumnService {

    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public MessageDto createBoardColumn(BoardColumnRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(() ->
            new IllegalArgumentException("해당 Board ID가 존재하지 않습니다."));

        BoardColumn boardColumn = new BoardColumn(requestDto, board);

        board.addUserBoardList(boardColumn);

        boardColumnRepository.save(boardColumn);

        return new MessageDto("컬럼이 추가되었습니다");
    }

    @Transactional
    public MessageUpdateDto updateBoardColumn(BoardColumnUpdateRequestDto requestDto) {
        System.out.println("update");
        System.out.println("requestDto.getColumnName() = " + requestDto.getColumnName());
        BoardColumn boardColumn = boardColumnRepository.findById(requestDto.getColumnId())
            .orElseThrow(() ->
                new IllegalArgumentException("해당 컬럼은 존재하지 않습니다."));

        boardColumn.updateName(requestDto.getColumnName());

        return new MessageUpdateDto("컬럼명이 수정되었습니다", requestDto.getColumnName());
    }

    public MessageDto deleteBoardColumn(Long columnId) {
        System.out.println("delete column");
        BoardColumn boardColumn = boardColumnRepository.findById(columnId).orElseThrow(() ->
            new IllegalArgumentException("해당 컬럼은 존재하지 않습니다."));
        boardColumnRepository.deleteById(columnId);
        return new MessageDto("해당 컬럼명이 삭제되었습니다");
    }

    @Transactional
    public BoardColumnMoveResponseDto moveBoardColumn(Long boardId,
        BoardColumnMoveRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
            new IllegalArgumentException("해당 Board ID가 존재하지 않습니다."));
        List<Long> columnsNos = requestDto.getColumnsNos();

        for (int i = 0; i < board.getBoardColumns().size(); i++) {
            board.getBoardColumns().get(i).setColumnNo(columnsNos.get(i));
        }

        return new BoardColumnMoveResponseDto(
            board.getBoardColumns().stream().map(n -> n.getColumnNo()).toList());
    }
}