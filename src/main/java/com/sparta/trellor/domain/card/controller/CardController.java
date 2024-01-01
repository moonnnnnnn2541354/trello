package com.sparta.trellor.domain.card.controller;

import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.dto.CommonResponseDto;
import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.card.service.CardService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RequestMapping("/api/{boardId}/{columnId}/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<?> createCards(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestBody CardRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ) {
        try {
            CardResponseDto cardResponseDto = cardService.createCards(boardId, columnId, cardRequestDto, userDetails);
            return ResponseEntity.ok().body(cardResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(
            @PathVariable String boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @RequestBody CardRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            CardResponseDto cardResponseDto = cardService.updateCard(Long.valueOf(boardId), columnId, cardId, cardRequestDto, userDetails);
            return ResponseEntity.ok().body(cardResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> deleteCard(
            @PathVariable String boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            cardService.deleteCard(cardId, userDetails);
            return ResponseEntity.ok().body(new CommonResponseDto("정상적으로 삭제 되었습니다.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/{cardId}/move")
    public ResponseEntity<?> moveCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @RequestParam(required = false) Long newColumnId,
            @RequestParam(required = false) Integer newPosition,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            if (newColumnId != null && newPosition != null) {
                // 카드를 다른 컬럼으로 이동하는 경우
                CardResponseDto cardResponseDto = cardService.moveCardToAnotherColumn(boardId, columnId, newColumnId, cardId, userDetails);
                return ResponseEntity.ok().body(cardResponseDto);
            } else if (newPosition != null) {
                // 같은 컬럼 내에서 카드 위치를 변경하는 경우
                CardResponseDto cardResponseDto = cardService.changeCardPositionInSameColumn(boardId, columnId, cardId, newPosition, userDetails);
                return ResponseEntity.ok().body(cardResponseDto);
            } else {
                // 필요한 매개변수가 제공되지 않는 경우
                return ResponseEntity.badRequest().body(new CommonResponseDto("카드를 올바르게 이동할 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
            }
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
