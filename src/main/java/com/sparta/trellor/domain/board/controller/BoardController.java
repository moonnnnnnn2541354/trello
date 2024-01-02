package com.sparta.trellor.domain.board.controller;


import com.sparta.trellor.domain.board.dto.*;
import com.sparta.trellor.domain.board.service.BoardService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<BoardInviteResponseDto> boardInvite(@RequestBody BoardInviteRequestDto requestDto) {
        BoardInviteResponseDto responseDto = boardService.boardInvite(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public BoardInviteResponseDto boardUpdate(@PathVariable Long boardId, @RequestBody BoardCreateRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.boardUpdate(boardId,requestDto,userDetails);
    }

    @DeleteMapping("/{boardId}")
    public BoardInviteResponseDto deleteBoard(@PathVariable Long boardId,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.deleteBoard(boardId,userDetails);
    }

}

