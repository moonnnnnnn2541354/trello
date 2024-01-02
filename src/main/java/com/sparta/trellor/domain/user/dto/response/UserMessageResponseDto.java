package com.sparta.trellor.domain.user.dto.response;

import com.sparta.trellor.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserMessageResponseDto {

    private String msg;
    private int status;
    private UserResponseDto data;

    @Builder
    public UserMessageResponseDto(String msg, int status, User user) {
        this.msg = msg;
        this.status = status;
        this.data = new UserResponseDto(user);
    }

    @Builder
    public UserMessageResponseDto(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }

}
