package com.sparta.trellor.domain.user.controller;

import com.sparta.trellor.domain.user.dto.request.EmailUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.PasswordUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.service.UserService;
import com.sparta.trellor.global.jwt.JwtUtil;
import com.sparta.trellor.global.security.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Long id = userService.deleteAccount(userId, userDetails.getUser());
        if(id != null) {
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
}
