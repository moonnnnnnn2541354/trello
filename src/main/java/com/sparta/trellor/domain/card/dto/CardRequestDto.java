package com.sparta.trellor.domain.card.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CardRequestDto {

    private Long id;
    @Pattern(regexp = "^.+$", message = "카드제목을 입력해주세요")
    private String cardTitle;
    @Pattern(regexp = "^.+$", message = "카드내용을 입력해주세요")
    private String cardInfo;
    @Pattern(regexp = "^.+$", message = "카드색상을 입력해주세요")
    private String cardColor;

}
