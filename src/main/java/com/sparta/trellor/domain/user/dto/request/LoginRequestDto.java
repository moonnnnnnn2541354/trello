package com.sparta.trellor.domain.user.dto.request;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}
