package com.sparta.trellor.domain.card.service;

import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.repository.BoardRepository;
import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.dto.msg.CardMessageDeleteDto;
import com.sparta.trellor.domain.card.dto.msg.CardMessageResponseDto;
import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.card.repository.CardRepository;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import com.sparta.trellor.domain.column.repository.BoardColumnRepository;
import com.sparta.trellor.domain.comment.entity.Comment;
import com.sparta.trellor.domain.comment.repository.CommentRepository;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public CardMessageResponseDto createCards(Long boardId, Long columnId, CardRequestDto cardRequestDto, UserDetailsImpl userDetails) {

        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        BoardColumn column = boardColumnRepository.findById(columnId).orElseThrow(NullPointerException::new);
        Card card = new Card(cardRequestDto, userDetails, board, column);

        Card saveCard = cardRepository.save(card);

        return new CardMessageResponseDto("카드를 작성하였습니다.", HttpStatus.CREATED.value(), saveCard);
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
    public CardMessageResponseDto updateCard(Long boardId, Long columnId, Long cardId, CardRequestDto cardRequestDto, UserDetailsImpl userDetails) {

        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        BoardColumn column = boardColumnRepository.findById(columnId).orElseThrow(NullPointerException::new);
        Card card = getUserCard(cardId, userDetails.getUser());

        card.setCardTitle(cardRequestDto.getCardTitle());
        card.setCardInfo(cardRequestDto.getCardInfo());
        card.setCardColor(cardRequestDto.getCardColor());

        return new CardMessageResponseDto("카드를 수정하였습니다.", HttpStatus.OK.value(), card);
    }

    @Transactional
    public CardMessageDeleteDto deleteCard(Long cardId, UserDetailsImpl userDetails) {
        Card card = getUserCard(cardId, userDetails.getUser());

        cardRepository.delete(card);

        return new CardMessageDeleteDto
                ("카드를 삭제 하였습니다", HttpStatus.OK.value());
    }

    @Transactional
    public CardMessageResponseDto changeCardPositionInSameColumn(Long boardId, Long columnId, Long cardId, int newCardNo, UserDetailsImpl userDetails) {
        BoardColumn column = boardColumnRepository.findById(columnId).orElseThrow(NullPointerException::new);
        Card card = getUserCard(cardId, userDetails.getUser());

        if (!column.getCards().contains(card)) {
            throw new IllegalArgumentException("현재 카드가 선택한 컬럼에 존재하지 않습니다.");
        }

        if (newCardNo < 0 || newCardNo >= column.getCards().size()) {
            throw new IllegalArgumentException("유효하지 않은 위치입니다.");
        }

        column.moveCardPosition(card, newCardNo);

        return new CardMessageResponseDto("카드를 이동하였습니다.", HttpStatus.OK.value(), card);
    }

    @Transactional
    public CardMessageResponseDto moveCardToAnotherColumn(Long boardId, Long currentColumnId, Long newColumnId, Long cardId, UserDetailsImpl userDetails) {
        BoardColumn currentColumn = boardColumnRepository.findById(currentColumnId).orElseThrow(NullPointerException::new);
        BoardColumn newColumn = boardColumnRepository.findById(newColumnId).orElseThrow(NullPointerException::new);
        Card card = getUserCard(cardId, userDetails.getUser());

        if (!currentColumn.getCards().contains(card)) {
            throw new IllegalArgumentException("현재 카드가 선택한 컬럼에 존재하지 않습니다.");
        }

        newColumn.addCard(card);

        return new CardMessageResponseDto("카드를 이동하였습니다.", HttpStatus.OK.value(), card);
    }

    private Card getCard(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    public Card getUserCard(Long cardId, User user) {
        Card card = getCard(cardId);

        if (!user.getUsername().equals(card.getUser().getUsername())) {
            throw new RejectedExecutionException("수정 권한이 없습니다.");
        }

        return card;
    }

}
