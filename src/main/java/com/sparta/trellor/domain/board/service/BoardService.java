package com.sparta.trellor.domain.board.service;



import com.sparta.trellor.domain.board.dto.*;
import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.entity.UserBoard;
import com.sparta.trellor.domain.board.repository.BoardRepository;
import com.sparta.trellor.domain.board.repository.UserBoardRepository;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.user.repository.UserRepository;
import com.sparta.trellor.global.jwt.SecurityUtil;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;

    private User user;
    private Board board;


    @Transactional
    public BoardCreateResponseDto createBoard(BoardCreateRequestDto requestDto) {
        Long userId = SecurityUtil.getPrincipal().get().getId();

        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("회원을 찾을 수 없습니다.")
        );

        Board board = new Board(requestDto, user);

        UserBoard userBoard = new UserBoard(user, board);

        boardRepository.save(board);
        userBoardRepository.save(userBoard);

        return new BoardCreateResponseDto(board);
    }

    public List<BoardReadResponseDto> readAllBoard() {
        return boardRepository.findAll().stream().map(BoardReadResponseDto::new).toList();
    }

    public BoardReadAllResponseDto readChoiceBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new UsernameNotFoundException("선택하신 Board는 존재하지 않습니다.")
        );

        return new BoardReadAllResponseDto(board);
    }

    public BoardInviteResponseDto boardInvite(BoardInviteRequestDto requestDto) {
        User inviteUser = userRepository.findByUsername(requestDto.getUserName()).orElseThrow(
                ()-> new UsernameNotFoundException("초대할 회원을 찾을 수 없습니다.")
        );
        Board inviteBoard = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                ()-> new UsernameNotFoundException("초대할 보드를 찾을 수 없습니다."));
        // 초대 로직 구현
        // boardId에 해당하는 보드에 invitedUserIds에 해당하는 사용자들을 초대하는 비즈니스 로직을 수행
        userBoardRepository.save(new UserBoard(inviteUser.getId(), inviteBoard.getBoardId()));
        // 성공 메시지를 담은 응답 객체를 생성하여 반환
        return new BoardInviteResponseDto("초대가 성공적으로 완료되었습니다.");
    }

    @Transactional
    public BoardInviteResponseDto boardUpdate(Long boardId, BoardCreateRequestDto requestDto,UserDetailsImpl userDetails) {
        user = userDetails.getUser();
        board = getBoard(boardId);

        if(user.getUsername().equals(board.getUser().getUsername())){
            board.update(requestDto);;
        }else{
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        return new BoardInviteResponseDto("수정되었습니다.");
    }

    @Transactional
    public BoardInviteResponseDto deleteBoard(Long boardId, UserDetailsImpl userDetails) {
        Board boards = boardRepository.findById(boardId)
                .orElseThrow(() -> new UsernameNotFoundException("삭제할 보드를 찾을 수 없습니다."));

        user = userDetails.getUser();
        board = getBoard(boardId);
        if(user.getUsername().equals(board.getUser().getUsername())){
            boardRepository.delete(board);
        }else{
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        return new BoardInviteResponseDto("보드가 삭제되었습니다.");
    }


    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 ID 입니다."));
    }


}

