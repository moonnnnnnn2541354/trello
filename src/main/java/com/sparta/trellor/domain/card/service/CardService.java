package com.sparta.trellor.domain.card.service;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.repository.BoardRepository;
import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.card.repository.CardRepository;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import com.sparta.trellor.domain.column.repository.BoardColumnRepository;
import com.sparta.trellor.domain.comment.entity.Comment;
import com.sparta.trellor.domain.comment.repository.CommentRepository;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CardService {

    private final BoardRepository boardRepository;
    private final BoardColumnRepository boardColumnRepository;
    private final CardRepository cardRepository;
    private final CommentRepository commentRepository;

    public CardResponseDto createCards(Long boardId, Long columnId, CardRequestDto cardRequestDto, UserDetailsImpl userDetails) {

        // 제목(title)과 내용(content)이 비어 있는 경우
        if (cardRequestDto.getCardTitle() == null || cardRequestDto.getCardTitle().trim().isEmpty()) {
            // 제목이 비어있을 때의 에러 메시지 처리
            throw new IllegalArgumentException("카드 제목이 비어있습니다. 제목을 작성해 주세요.");
        } else if (cardRequestDto.getCardInfo() == null || cardRequestDto.getCardInfo().trim().isEmpty()) {
            // 내용이 비어있을 때의 에러 메시지 처리
            throw new IllegalArgumentException("카드 내용이 비어있습니다. 내용을 작성해 주세요.");
        }

        //받아온 정보 객체에 저장
        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        BoardColumn column = boardColumnRepository.findById(columnId).orElseThrow(NullPointerException::new);
        Card card = new Card(cardRequestDto, userDetails, board, column);

        //DB에 저장
        Card saveCard = cardRepository.save(card);

        //DB에 저장된 값 반환
        return new CardResponseDto(saveCard);
    }

    public List<CardResponseDto> garAllCards() {
        List<Card> cardList = cardRepository.findAll();

        List<CardResponseDto> responseDtoList = new ArrayList<>();

        for (Card card : cardList) {
            responseDtoList.add(new CardResponseDto(card));
        }
        return responseDtoList;
    }

    public CardResponseDto getCardDto(Long boardId, Long columnId, Long cardId) {
        Card card = getCard(cardId);
        List<Comment> commentList = commentRepository.findAllByCardId(cardId);
        return new CardResponseDto(card, commentList);
    }

    @Transactional
    public CardResponseDto updateCard(Long boardId, Long columnId, Long cardId, CardRequestDto cardRequestDto, UserDetailsImpl userDetails) {

        // 제목(title)과 내용(info)이 비어 있는 경우
        if (cardRequestDto.getCardTitle() == null || cardRequestDto.getCardTitle().trim().isEmpty()) {
            // 제목이 비어있을 때의 에러 메시지 처리
            throw new IllegalArgumentException("카드 제목이 비어있습니다. 제목을 작성해 주세요.");
        } else if (cardRequestDto.getCardInfo() == null || cardRequestDto.getCardInfo().trim().isEmpty()) {
            // 내용이 비어있을 때의 에러 메시지 처리
            throw new IllegalArgumentException("카드 내용이 비어있습니다. 내용을 작성해 주세요.");
        }

        //받아온 정보 객체에 저장
        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        BoardColumn column = boardColumnRepository.findById(columnId).orElseThrow(NullPointerException::new);
        Card card = getUserCard(cardId, userDetails.getUser());

        card.setCardTitle(cardRequestDto.getCardTitle());
        card.setCardInfo(cardRequestDto.getCardInfo());
        card.setCardColor(cardRequestDto.getCardColor());

        return new CardResponseDto(card);
    }

    @Transactional
    public CardResponseDto changeCardPositionInSameColumn(Long boardId, Long columnId, Long cardId, int newCardNo, UserDetailsImpl userDetails) {
        BoardColumn column = boardColumnRepository.findById(columnId).orElseThrow(NullPointerException::new);
        Card card = getUserCard(cardId, userDetails.getUser());

        // 카드가 해당 컬럼에 있는지 확인
        if (!column.getCards().contains(card)) {
            throw new IllegalArgumentException("현재 카드가 선택한 컬럼에 존재하지 않습니다.");
        }

        // 새로운 위치가 유효한지 확인 (0 이상이고, 컬럼 내 카드의 개수보다 작거나 같아야 함)
        if (newCardNo < 0 || newCardNo >= column.getCards().size()) {
            throw new IllegalArgumentException("유효하지 않은 위치입니다.");
        }

        // 카드 위치 변경
        column.moveCardPosition(card, newCardNo);

        return new CardResponseDto(card);
    }

    @Transactional
    public CardResponseDto moveCardToAnotherColumn(Long boardId, Long currentColumnId, Long newColumnId, Long cardId, UserDetailsImpl userDetails) {
        BoardColumn currentColumn = boardColumnRepository.findById(currentColumnId).orElseThrow(NullPointerException::new);
        BoardColumn newColumn = boardColumnRepository.findById(newColumnId).orElseThrow(NullPointerException::new);
        Card card = getUserCard(cardId, userDetails.getUser());

        // 카드가 현재 컬럼에 있는지 확인
        if (!currentColumn.getCards().contains(card)) {
            throw new IllegalArgumentException("현재 카드가 선택한 컬럼에 존재하지 않습니다.");
        }

        // 카드를 새 컬럼으로 이동시킴
        newColumn.addCard(card);

        return new CardResponseDto(card);
    }

        //카드 예외처리
    private Card getCard(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    //카드 수정시 예외처리
    public Card getUserCard(Long cardId, User user) {
        Card card = getCard(cardId);

        if (!user.getUsername().equals(card.getUser().getUsername())) {
            throw new RejectedExecutionException("수정 권한이 없습니다.");
        }

        return card;
    }

    @Transactional
    public void deleteCard(Long cardId, UserDetailsImpl userDetails) {
        Card card = getUserCard(cardId, userDetails.getUser());

        cardRepository.delete(card);
    }
}
