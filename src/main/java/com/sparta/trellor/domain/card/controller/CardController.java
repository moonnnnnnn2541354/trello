package com.sparta.trellor.domain.card.controller;

import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.dto.CommonResponseDto;
import com.sparta.trellor.domain.card.service.CardService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/all")
    public List<CardResponseDto> grtAllCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId) {
        return cardService.garAllCards();
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<?> getCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId
    ) {
        try {
            CardResponseDto cardResponseDto = cardService.getCardDto(boardId, columnId, cardId);
            return ResponseEntity.ok().body(cardResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @RequestBody CardRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            CardResponseDto cardResponseDto = cardService.updateCard(boardId, columnId, cardId, cardRequestDto, userDetails);
            return ResponseEntity.ok().body(cardResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CommonResponseDto> deleteCard(
            @PathVariable Long boardId,
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
                CardResponseDto cardResponseDto = cardService.moveCardToAnotherColumn(boardId, columnId, newColumnId, cardId, userDetails);
                return ResponseEntity.ok().body(cardResponseDto);
            } else if (newPosition != null) {
                CardResponseDto cardResponseDto = cardService.changeCardPositionInSameColumn(boardId, columnId, cardId, newPosition, userDetails);
                return ResponseEntity.ok().body(cardResponseDto);
            } else {
                return ResponseEntity.badRequest().body(new CommonResponseDto("카드를 올바르게 이동할 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
            }
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
