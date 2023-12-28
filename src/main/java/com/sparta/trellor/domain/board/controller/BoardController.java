package com.sparta.trellor.domain.board.controller;


import com.sparta.trellor.domain.board.dto.*;
import com.sparta.trellor.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @GetMapping("/all")
    public List<BoardReadAllResponseDto> readAllBoard() {
        return boardService.readAllBoard();
    }

    @GetMapping("/{boardId}")
    public BoardReadAllResponseDto readChoiceBoard(@PathVariable Long boardId) {
        return boardService.readChoiceBoard(boardId);
    }

    @PostMapping
    public BoardCreateResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto)  {
        System.out.println("createBoard called with: " + requestDto);
        return boardService.createBoard(requestDto);
    }
    @PostMapping("/invite")
    public BoardInviteResponseDto boardInvite(@RequestBody BoardInviteRequestDto requestDto) {
        return boardService.boardInvite(requestDto);
    }

    @PutMapping("/{boardId}")
    public BoardInviteResponseDto boardUpdate(@PathVariable Long boardId, @RequestBody BoardCreateRequestDto requestDto){
        return boardService.boardUpdate(boardId,requestDto);
    }

    @DeleteMapping("/{boardId}")
    public BoardInviteResponseDto deleteBoard(@PathVariable Long boardId){
        return boardService.deleteBoard(boardId);
    }

}

