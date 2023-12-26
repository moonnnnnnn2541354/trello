package com.sparta.trellor.domain.board.controller;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        List<Board> boards = boardService.getAllBoards();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Board> createBoard(@RequestBody Map<String, String> boardData) {
        String boardName = boardData.get("boardName");
        String backgroundColor = boardData.get("backgroundColor");
        String boardInfo = boardData.get("boardInfo");
        Board createdBoard = boardService.createBoard(boardName, backgroundColor,boardInfo);
        return new ResponseEntity<>(createdBoard, HttpStatus.CREATED);
    }

//
//    @PutMapping("/{boardId}/update")
//    public ResponseEntity<Board> updateBoard(
//            @PathVariable Long boardId,
//            @RequestParam String boardName,
//            @RequestParam String backgroundColor) {
//        Board updatedBoard = boardService.updateBoard(boardId, boardName, backgroundColor);
//        return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{boardId}/delete")
//    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
//        boardService.deleteBoard(boardId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
