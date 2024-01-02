package com.sparta.trellor.domain.user.controller;

import com.sparta.trellor.domain.user.dto.request.EmailUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.PasswordUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.dto.response.UserMessageResponseDto;
import com.sparta.trellor.domain.user.service.UserService;
import com.sparta.trellor.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final int CREATED = HttpStatus.CREATED.value();
    private final int CONFLICT = HttpStatus.CONFLICT.value();
    private final int OK = HttpStatus.OK.value();
    private final int FORBIDDEN = HttpStatus.FORBIDDEN.value();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
        @Valid @RequestBody SignupRequestDto requestDto,
        BindingResult bindingResult
    ) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<String> errorMessages = new ArrayList<>();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : fieldErrors) {
                errorMessages.add(fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(403).body(errorMessages);
        }

        UserMessageResponseDto responseDto = userService.signup(requestDto);

        if (responseDto.getStatus() == CREATED) {
            return ResponseEntity.status(CREATED).body(responseDto);
        } else {
            return ResponseEntity.status(CONFLICT).body(responseDto);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(
        @PathVariable Long userId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserMessageResponseDto responseDto = userService.deleteAccount(userId,
            userDetails.getUser());
        if (responseDto.getStatus() == OK) {
            return ResponseEntity.status(OK).body(responseDto);
        } else {
            return ResponseEntity.status(FORBIDDEN).body(responseDto);
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(
        @PathVariable Long userId,
        @Valid @RequestBody PasswordUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserMessageResponseDto responseDto = userService.updatePassword(userId, requestDto,
            userDetails.getUser());
        if (responseDto.getStatus() == OK) {
            return ResponseEntity.status(OK).body(responseDto);
        } else {
            return ResponseEntity.status(FORBIDDEN).body(responseDto);
        }
    }

    @PutMapping("/{userId}/email")
    public ResponseEntity<?> updateEmail(
        @PathVariable Long userId,
        @Valid @RequestBody EmailUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UserMessageResponseDto responseDto = userService.updateEmail(userId, requestDto,
            userDetails.getUser());
        if (responseDto.getStatus() == OK) {
            return ResponseEntity.status(OK).body(responseDto);
        } else {
            return ResponseEntity.status(FORBIDDEN).body(responseDto);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        UserMessageResponseDto responseDto = userService.logout(request, response);
        return ResponseEntity.status(OK).body(responseDto);
    }
}
