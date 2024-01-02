package com.sparta.trellor.domain.column.service;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.repository.BoardRepository;
import com.sparta.trellor.domain.column.dto.BoardColumnMoveRequestDto;
import com.sparta.trellor.domain.column.dto.BoardColumnMoveResponseDto;
import com.sparta.trellor.domain.column.dto.BoardColumnRequestDto;
import com.sparta.trellor.domain.column.dto.BoardColumnUpdateRequestDto;
import com.sparta.trellor.domain.column.dto.MessageDto;
import com.sparta.trellor.domain.column.dto.MessageUpdateDto;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import com.sparta.trellor.domain.column.repository.BoardColumnRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardColumnService {
    private final BoardColumnRepository boardColumnRepository;
    private final BoardRepository boardRepository;

    public BoardColumn findBoardColumn(Long columnId) {
        BoardColumn boardColumn = boardColumnRepository.findById(columnId).orElseThrow(() ->
            new IllegalArgumentException("해당 컬럼은 존재하지 않습니다."));
        return boardColumn;
    }
    public MessageDto createBoardColumn(BoardColumnRequestDto requestDto) {
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(() ->
            new IllegalArgumentException("해당 Board ID가 존재하지 않습니다."));

        BoardColumn boardColumn = new BoardColumn(requestDto,board);

        board.addUserBoardList(boardColumn);

//        findBoardColumn(boardColumn);

        boardColumnRepository.save(boardColumn);

        return new MessageDto("컬럼이 추가되었습니다");
    }

    @Transactional
    public void findBoardColumn (BoardColumn boardColumn){
        BoardColumn boardColumnTemp = boardColumnRepository.findByColumnName(boardColumn.getColumnName()).orElseThrow(
            ()-> new IllegalArgumentException("해당 컬럼 없음")
        );
        boardColumnTemp.update(boardColumn.getColumnId());
    }

    @Transactional
    public MessageUpdateDto updateBoardColumn(BoardColumnUpdateRequestDto requestDto) {
        System.out.println("update");
        System.out.println("requestDto.getColumnName() = " + requestDto.getColumnName());
        BoardColumn boardColumn = boardColumnRepository.findById(requestDto.getColumnId()).orElseThrow(() ->
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
    public BoardColumnMoveResponseDto moveBoardColumn(Long boardId, BoardColumnMoveRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() ->
            new IllegalArgumentException("해당 Board ID가 존재하지 않습니다."));
        List<Long> columnsNos = requestDto.getColumnsNos();

        for (int i = 0; i < board.getBoardColumns().size(); i++)
            board.getBoardColumns().get(i).setColumnNo(columnsNos.get(i));

        return new BoardColumnMoveResponseDto(board.getBoardColumns().stream().map(n->n.getColumnNo()).toList());
    }
}