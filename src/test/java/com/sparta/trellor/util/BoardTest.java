package com.sparta.trellor.util;

import com.sparta.trellor.domain.board.dto.BoardCreateRequestDto;
import com.sparta.trellor.domain.board.dto.BoardCreateResponseDto;
import com.sparta.trellor.domain.board.dto.BoardInviteRequestDto;
import com.sparta.trellor.domain.board.dto.BoardInviteResponseDto;
import com.sparta.trellor.domain.board.entity.Board;
import com.sparta.trellor.domain.board.entity.UserBoard;
import java.util.Arrays;
import java.util.List;

public interface BoardTest extends CommonTest {

    Long TEST_BOARD_ID = 1L;
    String TEST_BOARD_NAME = "name";
    String TEST_BOARD_COLOR = "color";
    String TEST_BOARD_INFO = "info";
    String MESSAGE = "msg";

    BoardCreateRequestDto TEST_BOARD_CREATE_REQUEST_DTO =
        BoardCreateRequestDto.builder()
            .boardName(TEST_BOARD_NAME)
            .boardColor(TEST_BOARD_COLOR)
            .boardInfo(TEST_BOARD_INFO)
            .build();

    BoardCreateResponseDto TEST_BOARD_CREATE_RESPONSE_DTO =
        BoardCreateResponseDto.builder()
            .boardName(TEST_USER_NAME)
            .boardColor(TEST_BOARD_COLOR)
            .boardInfo(TEST_BOARD_INFO)
            .build();

    BoardInviteRequestDto TEST_BOARD_INVITE_REQUEST_DTO =
        BoardInviteRequestDto.builder()
            .boardId(TEST_BOARD_ID)
            .userName(TEST_ANOTHER_USER.getUsername())
            .build();

    BoardInviteResponseDto TEST_BOARD_INVITE_RESPONSE_DTO =
        BoardInviteResponseDto.builder()
            .msg(MESSAGE)
            .build();

    Board TEST_BOARD = new Board(TEST_BOARD_CREATE_REQUEST_DTO, TEST_USER);

    UserBoard TEST_USER_BOARD = new UserBoard(TEST_USER, TEST_BOARD);
    UserBoard TEST_ANOTHER_USER_BOARD = new UserBoard(TEST_ANOTHER_USER_ID,TEST_BOARD_ID);
    List<UserBoard> TEST_USER_BOARD_LIST = Arrays.asList(TEST_USER_BOARD,TEST_ANOTHER_USER_BOARD);
}
