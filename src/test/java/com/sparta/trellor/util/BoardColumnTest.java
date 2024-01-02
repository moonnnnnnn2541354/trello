package com.sparta.trellor.util;

import com.sparta.trellor.domain.column.dto.BoardColumnRequestDto;
import com.sparta.trellor.domain.column.entity.BoardColumn;
import java.util.Arrays;
import java.util.List;

public interface BoardColumnTest extends CommonTest {

    Long TEST_COLUMN_ID_1 = 1L;
    Long TEST_COLUMN_ID_2 = 2L;
    String TEST_COLUMN_NAME_1 = "name";
    String TEST_COLUMN_NAME_2 = "name2";
    Long TEST_COLUMN_NO_1 = 1L;
    Long TEST_COLUMN_NO_2 = 2L;

    BoardColumnRequestDto TEST_COLUMN_REQUEST_DTO_1 =
        BoardColumnRequestDto.builder()
            .boardId(BoardTest.TEST_BOARD_ID)
            .columnName(TEST_COLUMN_NAME_1)
            .build();

    BoardColumnRequestDto TEST_COLUMN_REQUEST_DTO_2 =
        BoardColumnRequestDto.builder()
            .boardId(BoardTest.TEST_BOARD_ID)
            .columnName(TEST_COLUMN_NAME_2)
            .build();

    BoardColumn TEST_COLUMN_1 = BoardColumn.builder()
        .columnId(TEST_COLUMN_ID_1)
        .columnName(TEST_COLUMN_NAME_1)
        .columnNo(TEST_COLUMN_NO_1)
        .cards(CardTest.TEST_CARD_LIST)
        .build();

    BoardColumn TEST_COLUMN_2 = BoardColumn.builder()
        .columnId(TEST_COLUMN_ID_2)
        .columnName(TEST_COLUMN_NAME_1)
        .columnNo(TEST_COLUMN_NO_2)
        .cards(CardTest.TEST_CARD_LIST)
        .build();

    List<BoardColumn> TEST_COLUMN_LIST = Arrays.asList(TEST_COLUMN_1,TEST_COLUMN_2);
}
