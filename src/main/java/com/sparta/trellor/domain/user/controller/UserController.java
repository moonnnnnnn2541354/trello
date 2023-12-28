package com.sparta.trellor.domain.user.controller;

import com.sparta.trellor.domain.user.dto.request.EmailUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.PasswordUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.service.UserService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        if(userService.signup(requestDto))
            return ResponseEntity.status(201).body("회원가입 성공했습니다.");
        else
            return ResponseEntity.status(409).body("이미 존재하는 사용자입니다.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long id = userService.deleteAccount(userId, userDetails.getUser());
        if (id != null) {
            return ResponseEntity.status(200).body("회원탈퇴가 완료되었습니다.");
        }
        return ResponseEntity.status(301).body("회원탈퇴를 실패하였습니다.");
    }

    @PutMapping("/{userId}/password")
    public void updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody PasswordUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.updatePassword(userId, requestDto, userDetails.getUser());
    }

    @PutMapping("/{userId}/email")
    public void updateEmail(
            @PathVariable Long userId,
            @Valid @RequestBody EmailUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.updateEmail(userId, requestDto, userDetails.getUser());
    }

    @GetMapping("/logout")
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        userService.logout(request, response);
    }
}
