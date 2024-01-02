package com.sparta.trellor.util;

import com.sparta.trellor.domain.card.dto.CardRequestDto;
import com.sparta.trellor.domain.card.dto.CardResponseDto;
import com.sparta.trellor.domain.card.entity.Card;
import java.util.Arrays;
import java.util.List;

public interface CardTest extends CommonTest {

    Long TEST_CARD_ID_1 = 1L;
    Long TEST_CARD_ID_2 = 2L;
    String TEST_CARD_TITLE = "title";
    String TEST_CARD_INFO = "info";
    String TEST_CARD_COLOR = "color";

    CardRequestDto TEST_CARD_REQUEST_DTO_1 =
        CardRequestDto.builder()
            .id(TEST_CARD_ID_1)
            .cardTitle(TEST_CARD_TITLE)
            .cardInfo(TEST_CARD_INFO)
            .cardColor(TEST_CARD_COLOR)
            .build();

    CardRequestDto TEST_CARD_REQUEST_DTO_2 =
        CardRequestDto.builder()
            .id(TEST_CARD_ID_2)
            .cardTitle(TEST_CARD_TITLE)
            .cardInfo(TEST_CARD_INFO)
            .cardColor(TEST_CARD_COLOR)
            .build();

    Card TEST_CARD_1 = Card.builder()
        .id(TEST_CARD_ID_1)
        .title(TEST_CARD_TITLE)
        .info(TEST_CARD_INFO)
        .color(TEST_CARD_COLOR)
        .user(TEST_USER)
        .board(BoardTest.TEST_BOARD)
        .column(BoardColumnTest.TEST_COLUMN_1)
        .build();

    Card TEST_CARD_2 = Card.builder()
        .id(TEST_CARD_ID_2)
        .title(TEST_CARD_TITLE)
        .info(TEST_CARD_INFO)
        .color(TEST_CARD_COLOR)
        .user(TEST_USER)
        .board(BoardTest.TEST_BOARD)
        .column(BoardColumnTest.TEST_COLUMN_1)
        .build();

    CardResponseDto TEST_CARD_RESPONSE_DTO = new CardResponseDto(TEST_CARD_1);

    List<Card> TEST_CARD_LIST = Arrays.asList(TEST_CARD_1, TEST_CARD_2);

}
