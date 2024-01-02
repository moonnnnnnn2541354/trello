package com.sparta.trellor.domain.comment.service;

import com.sparta.trellor.domain.card.entity.Card;
import com.sparta.trellor.domain.card.repository.CardRepository;
import com.sparta.trellor.domain.comment.dto.msg.MessageDeleteDto;
import com.sparta.trellor.domain.comment.dto.msg.MessageResponseDto;
import com.sparta.trellor.domain.comment.dto.request.CommentRequestDto;
import com.sparta.trellor.domain.comment.entity.Comment;
import com.sparta.trellor.domain.comment.exception.CommentErrorCode;
import com.sparta.trellor.domain.comment.exception.CommentExistsException;
import com.sparta.trellor.domain.comment.repository.CommentRepository;
import com.sparta.trellor.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    public MessageResponseDto create(
        User user, Long cardId, CommentRequestDto requestDto) {

        Card card = checkCard(cardId);
        Comment comment = Comment.builder()
            .username(user.getUsername())
            .comment_info(requestDto.getInfo())
            .card(card)
            .build();

        commentRepository.save(comment);

        return new MessageResponseDto
            ("댓글을 작성 하였습니다", HttpStatus.CREATED.value(), comment);
    }

    public MessageResponseDto update(
        User user, Long cardId, Long commentId, CommentRequestDto requestDto) {

        Card card = checkCard(cardId);
        Comment comment = checkComment(commentId);
        checkUser(comment, user);
        comment.update(requestDto);

        return new MessageResponseDto
            ("댓글을 수정 하였습니다", HttpStatus.OK.value(), comment);
    }

    public MessageDeleteDto delete(User user, Long cardId, Long commentId) {

        Card card = checkCard(cardId);
        Comment comment = checkComment(commentId);
        checkUser(comment, user);
        commentRepository.delete(comment);

        return new MessageDeleteDto
            ("댓글을 삭제 하였습니다", HttpStatus.OK.value());
    }

    private Card checkCard(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
            () -> new CommentExistsException(CommentErrorCode.NOT_EXISTS_CARD));
    }

    private Comment checkComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new CommentExistsException(CommentErrorCode.NOT_EXISTS_COMMENT));
    }

    private void checkUser(Comment comment, User user) {
        if (!comment.getUsername().equals(user.getUsername())) {
            throw new CommentExistsException(CommentErrorCode.UNAUTHORIZED_USER);
        }
    }

}
