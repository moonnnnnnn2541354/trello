package com.sparta.trellor.domain.comment.service;

import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.card.repository.CardRepository;
import com.sparta.trellor.domain.comment.dto.request.CommentRequestDto;
import com.sparta.trellor.domain.comment.dto.response.CommentResponseDto;
import com.sparta.trellor.domain.comment.entity.Comment;
import com.sparta.trellor.domain.comment.repository.CommentRepository;
import com.sparta.trellor.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public CommentResponseDto create(
        User user, Long cardId, CommentRequestDto requestDto) {

        Card card = checkCard(cardId);
        Comment comment = Comment.builder()
            .username(user.getUsername())
            .comment_info(requestDto.getInfo())
            .card(card)
            .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto update(
        User user, Long cardId, Long commentId, CommentRequestDto requestDto) {

        Card card = checkCard(cardId);
        Comment comment = checkComment(commentId);
        checkUser(comment, user);
        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    public void delete(
        User user, Long cardId, Long commentId, CommentRequestDto requestDto) {

        Card card = checkCard(cardId);
        Comment comment = checkComment(commentId);
        checkUser(comment, user);
        commentRepository.delete(comment);
    }


    ////////////////////////////////////////////////////////////
    private Card checkCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new NullPointerException("해당 카드가 존재하지 않습니다."));
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new NullPointerException("해당 댓글이 존재하지 않습니다."));
    }

    private void checkUser(Comment comment, User user) {
        if (!comment.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("유저정보가 일치하지 않습니다.");
        }
    }
    ////////////////////////////////////////////////////////////

}
