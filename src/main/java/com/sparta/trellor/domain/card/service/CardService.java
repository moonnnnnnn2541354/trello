package com.sparta.trellor.domain.card.service;

import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.card.repository.CardRepository;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    public CardResponseDto createCards(CardRequestDto cardRequestDto, UserDetailsImpl userDetails) {

        // 제목(title)과 내용(content)이 비어 있는 경우
        if (cardRequestDto.getCardTitle() == null || cardRequestDto.getCardTitle().trim().isEmpty()) {
            // 제목이 비어있을 때의 에러 메시지 처리
            throw new IllegalArgumentException("카드 제목이 비어있습니다. 제목을 작성해 주세요.");
        } else if (cardRequestDto.getCardInfo() == null || cardRequestDto.getCardInfo().trim().isEmpty()) {
            // 내용이 비어있을 때의 에러 메시지 처리
            throw new IllegalArgumentException("카드 내용이 비어있습니다. 내용을 작성해 주세요.");
        }

    //받아온 정보 객체에 저장
    Card card = new Card(cardRequestDto, userDetails);

    //DB에 저장
    Card saveCard = cardRepository.save(card);

    //DB에 저장된 값 반환
        return new CardResponseDto(saveCard);
    }
}
