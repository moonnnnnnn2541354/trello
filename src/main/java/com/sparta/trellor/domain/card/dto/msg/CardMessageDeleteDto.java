package com.sparta.trellor.domain.card.dto.msg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CardMessageDeleteDto {

    private String msg;
    private int status;

    @Builder
    public CardMessageDeleteDto(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}
