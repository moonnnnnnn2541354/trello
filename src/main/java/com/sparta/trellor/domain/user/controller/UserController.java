package com.sparta.trellor.domain.user.controller;

import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
    }
}
