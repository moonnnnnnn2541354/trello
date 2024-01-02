package com.sparta.trellor.domain.column.controller;

import com.sparta.trellor.domain.column.dto.*;
import com.sparta.trellor.domain.column.service.BoardColumnService;
import com.sparta.trellor.domain.utils.BaseTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/column")
@RequiredArgsConstructor
public class BoardColumnController extends BaseTime {

    private final BoardColumnService columnService;

    @PostMapping
    public MessageDto createBoardColumn(@RequestBody BoardColumnRequestDto requestDto) {
        return columnService.createBoardColumn(requestDto);
    }

    @PutMapping
    public MessageUpdateDto updateBoardColumn(@RequestBody BoardColumnUpdateRequestDto requestDto) {
        return columnService.updateBoardColumn(requestDto);
    }

    @PutMapping("/{boardId}")
    public BoardColumnMoveResponseDto moveBoardColumn(@PathVariable Long boardId,
        @RequestBody BoardColumnMoveRequestDto requestDto) {
        return columnService.moveBoardColumn(boardId, requestDto);
    }

    @DeleteMapping("/{columnId}")
    public MessageDto deleteBoardColumn(@PathVariable Long columnId) {
        return columnService.deleteBoardColumn(columnId);
    }
}
