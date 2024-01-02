package com.sparta.trellor.domain.board.controller;


import com.sparta.trellor.domain.board.dto.BoardCreateRequestDto;
import com.sparta.trellor.domain.board.dto.BoardCreateResponseDto;
import com.sparta.trellor.domain.board.dto.BoardInviteRequestDto;
import com.sparta.trellor.domain.board.dto.BoardInviteResponseDto;
import com.sparta.trellor.domain.board.dto.BoardReadAllResponseDto;
import com.sparta.trellor.domain.board.dto.BoardReadResponseDto;
import com.sparta.trellor.domain.board.service.BoardService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/all")
    public List<BoardReadResponseDto> readAllBoard() {
        return boardService.readAllBoard();
    }

    @GetMapping("/{boardId}")
    public BoardReadAllResponseDto readChoiceBoard(@PathVariable Long boardId) {
        return boardService.readChoiceBoard(boardId);
    }

    @PostMapping
    public BoardCreateResponseDto createBoard(@RequestBody BoardCreateRequestDto requestDto) {
        System.out.println("createBoard called with: " + requestDto);
        return boardService.createBoard(requestDto);
    }

    @PostMapping("/invite")
    public ResponseEntity<BoardInviteResponseDto> boardInvite(
        @RequestBody BoardInviteRequestDto requestDto) {
        BoardInviteResponseDto responseDto = boardService.boardInvite(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public BoardInviteResponseDto boardUpdate(@PathVariable Long boardId,
        @RequestBody BoardCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.boardUpdate(boardId, requestDto, userDetails);
    }

    @DeleteMapping("/{boardId}")
    public BoardInviteResponseDto deleteBoard(@PathVariable Long boardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(boardId, userDetails);
    }

}

