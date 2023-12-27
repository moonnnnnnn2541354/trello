package com.sparta.trellor.domain.user.controller;

import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.service.UserService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignupRequestDto requestDto) {
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
}
