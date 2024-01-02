package com.sparta.trellor.domain.card.controller;

import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.dto.CommonResponseDto;
import com.sparta.trellor.domain.card.dto.msg.CardMessageDeleteDto;
import com.sparta.trellor.domain.card.dto.msg.CardMessageResponseDto;
import com.sparta.trellor.domain.card.service.CardService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/{boardId}/{columnId}/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardMessageResponseDto> createCards(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @RequestBody CardRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CardMessageResponseDto cardResponseDto =
                cardService.createCards(boardId, columnId, cardRequestDto, userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponseDto);
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
    public ResponseEntity<CardMessageResponseDto> updateCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @RequestBody CardRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CardMessageResponseDto responseDto =
                cardService.updateCard(boardId, columnId, cardId, cardRequestDto, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<CardMessageDeleteDto> deleteCard(
            @PathVariable Long boardId,
            @PathVariable Long columnId,
            @PathVariable Long cardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CardMessageDeleteDto responseDto =
                cardService.deleteCard(cardId, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
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
        if (newColumnId != null && newPosition != null) {
            CardMessageResponseDto cardResponseDto = cardService.moveCardToAnotherColumn(boardId, columnId, newColumnId, cardId, userDetails);
            return ResponseEntity.ok().body(cardResponseDto);
        } else if (newPosition != null) {
            CardMessageResponseDto cardResponseDto = cardService.changeCardPositionInSameColumn(boardId, columnId, cardId, newPosition, userDetails);
            return ResponseEntity.ok().body(cardResponseDto);
        } else {
            return ResponseEntity.badRequest().body(new CommonResponseDto("카드를 올바르게 이동할 수 없습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
