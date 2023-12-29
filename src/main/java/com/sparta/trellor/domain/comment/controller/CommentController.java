package com.sparta.trellor.domain.comment.controller;

import com.sparta.trellor.domain.comment.dto.request.CommentRequestDto;
import com.sparta.trellor.domain.comment.dto.response.CommentResponseDto;
import com.sparta.trellor.domain.comment.service.CommentService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/{cardId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create(
        @AuthenticationPrincipal UserDetailsImpl userdetails,
        @PathVariable(name = "cardId") Long cardId,
        @Valid @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto =
            commentService.create(userdetails.getUser(), cardId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> update(
        @AuthenticationPrincipal UserDetailsImpl userdetails,
        @PathVariable(name = "cardId") Long cardId,
        @PathVariable(name = "commentId") Long commentId,
        @Valid @RequestBody CommentRequestDto requestDto) {

        CommentResponseDto responseDto =
            commentService.update(userdetails.getUser(), cardId, commentId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete(
        @AuthenticationPrincipal UserDetailsImpl userdetails,
        @PathVariable(name = "cardId") Long cardId,
        @PathVariable(name = "commentId") Long commentId) {

        commentService.delete(userdetails.getUser(), cardId, commentId);

        return ResponseEntity.status(HttpStatus.OK).body("댓글이 삭제 되었습니다.");
    }

}
