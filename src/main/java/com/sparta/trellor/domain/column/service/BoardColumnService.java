package com.sparta.trellor.domain.column.service;

import com.sparta.trellor.domain.column.dto.BoardColumnRequestDto;
import com.sparta.trellor.domain.column.dto.BoardColumnUpdateRequestDto;
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

    public BoardColumn findBoardColumn(Long columnId) {
        BoardColumn boardColumn = boardColumnRepository.findById(columnId).orElseThrow(() ->
            new IllegalArgumentException("해당 컬럼은 존재하지 않습니다."));
        return boardColumn;
    }

    @Transactional
    public void findBoardColumn (BoardColumn boardColumn){
        BoardColumn boardColumnTemp = boardColumnRepository.findByColumnName(boardColumn.getColumnName()).orElseThrow(
            ()-> new IllegalArgumentException("컬럼 없음")
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

        return new MessageUpdateDto("해당 컬럼명이 수정되었습니다", requestDto.getColumnName());
    }

}