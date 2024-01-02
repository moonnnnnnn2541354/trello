package com.sparta.trellor.domain.user.service;

import com.sparta.trellor.domain.user.dto.request.EmailUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.PasswordUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.dto.response.UserMessageResponseDto;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.user.entity.UserRoleEnum;
import com.sparta.trellor.domain.user.exception.UserExistsException;
import com.sparta.trellor.domain.user.repository.UserRepository;
import com.sparta.trellor.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sparta.trellor.domain.comment.exception.CommentErrorCode.UNAUTHORIZED_USER;
import static com.sparta.trellor.domain.user.exception.UserErrorCode.NOT_EXISTS_USER;

/**
 * 회원가입 관련 메서드
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * 회원가입 관련 메서드
     */
    public UserMessageResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        Optional<User> user = userRepository.findByUsernameAndEmail(username, email);

        User newUser = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(UserRoleEnum.USER)
                .build();

        if (user.isPresent()) {
            return new UserMessageResponseDto(
                    "이미 존재하는 사용자입니다.", HttpStatus.CONFLICT.value());
        }

        userRepository.save(newUser);
        return new UserMessageResponseDto(
                "회원가입 성공했습니다.", HttpStatus.CREATED.value(), newUser);
    }

    /**
     * 회원 탈퇴 관련 메서드
     */
    public UserMessageResponseDto deleteAccount(Long userId, User user) {
        User findUser = checkToExistUser(userId);
        checkAccessAuthority(findUser, user);
        userRepository.delete(user);
        return new UserMessageResponseDto(
                "회원탈퇴 성공했습니다.", HttpStatus.OK.value());
    }

    /**
     * 비밀번호 변경 관련 메서드
     */
    @Transactional
    public UserMessageResponseDto updatePassword(Long userId, PasswordUpdateRequestDto requestDto, User user) {
        User findUser = checkToExistUser(userId);
        checkAccessAuthority(findUser, user);
        boolean match = checkPasswordMatch(requestDto.getCurrentPassword(), user.getPassword());

        if(!match) {
            return new UserMessageResponseDto(
                    "비밀번호가 일치하지 않습니다.", HttpStatus.FORBIDDEN.value(), findUser);
        }
        findUser.passwordUpdate(passwordEncoder.encode(requestDto.getPassword()));
        return new UserMessageResponseDto(
                "비밀번호가 수정되었습니다.", HttpStatus.OK.value(), findUser);
    }

    /**
     * 이메일 변경 관련 메서드
     */
    @Transactional
    public UserMessageResponseDto updateEmail(Long userId, EmailUpdateRequestDto requestDto, User user) {
        User findUser = checkToExistUser(userId);
        checkAccessAuthority(findUser, user);
        boolean match = checkPasswordMatch(requestDto.getPassword(), user.getPassword());
        if(!match) {
            return new UserMessageResponseDto(
                    "비밀번호가 일치하지 않습니다.", HttpStatus.FORBIDDEN.value(), findUser);
        }
        findUser.emailUpdate(requestDto.getEmail());
        return new UserMessageResponseDto(
                "이메일이 수정되었습니다.", HttpStatus.OK.value(), findUser);
    }

    /**
     * 로그아웃 관련 메서드
     */
    public UserMessageResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        jwtUtil.deleteCookie(request, response);

        return new UserMessageResponseDto("로그아웃을 완료하였습니다.", HttpStatus.OK.value());
    }

    /**
     * 비밀번호 일치 여부 확인하는 메서드
     */
    private boolean checkPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    ////////////////////////////////////////////////////////////

    /**
     * 존재하는 사용자인지 확인하는 메서드
     */
    private User checkToExistUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserExistsException(NOT_EXISTS_USER));
    }

    /**
     * 접근 권한을 가지고 있는지 확인하는 메서드
     */
    private void checkAccessAuthority(User findUser, User user) {
        if (!findUser.getUsername().equals(user.getUsername())) {
            throw new UserExistsException(UNAUTHORIZED_USER);
        }
    }
    ////////////////////////////////////////////////////////////
}
