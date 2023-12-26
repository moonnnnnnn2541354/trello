package com.sparta.trellor.domain.board.service;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    //전체 보드 조회
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    //특정보드 조회
    public List<Board> getBoardsByOwnerId(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }


    //보드 생성
    public Board createBoard(String boardName,String backgroundColor,String boardInfo) {
        Board newBoard = new Board();
        newBoard.setBoardName(boardName);
        newBoard.setBoardColor(backgroundColor);
        newBoard.setBoardInfo(boardInfo);
        return boardRepository.save(newBoard);
    }


    private Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("보드의 아이디를 찾지 못했습니다. " + boardId));
    }



}
