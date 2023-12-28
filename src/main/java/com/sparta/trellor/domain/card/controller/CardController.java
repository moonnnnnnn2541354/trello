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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.RejectedExecutionException;

@RequestMapping("/api/cards")
@RestController
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<?> createCards(
            @RequestBody CardRequestDto cardRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            CardResponseDto cardResponseDto = cardService.createCards(cardRequestDto, userDetails);
            return ResponseEntity.ok().body(cardResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
