package com.sparta.trellor.domain.user.service;

import com.sparta.trellor.domain.user.dto.request.EmailUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.PasswordUpdateRequestDto;
import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.user.entity.UserRoleEnum;
import com.sparta.trellor.domain.user.repository.UserRepository;
import com.sparta.trellor.global.jwt.JwtUtil;
import com.sparta.trellor.global.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 회원가입 관련 메서드
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /**
     * 회원가입 관련 메서드
     */
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> user = userRepository.findByUsernameAndEmail(username, email);

        if(user.isEmpty()) {
            userRepository.save(new User(username, email, password, UserRoleEnum.USER));
        } else {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
    }

    /**
     * 회원 탈퇴 관련 메서드
     */
    public Long deleteAccount(Long userId, User user) {
        User findUser = checkToExistUser(userId);
        checkAccessAuthority(findUser, user);

        userRepository.delete(user);
        return user.getId();
    }

    /**
     * 비밀번호 변경 관련 메서드
     */
    @Transactional
    public void updatePassword(Long userId, PasswordUpdateRequestDto requestDto, User user) {
        User findUser = checkToExistUser(userId);
        checkAccessAuthority(findUser, user);

        if(passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            findUser.passwordUpdate(passwordEncoder.encode(requestDto.getPassword()));
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 이메일 변경 관련 메서드
     */
    @Transactional
    public void updateEmail(Long userId, EmailUpdateRequestDto requestDto, User user) {
        User findUser = checkToExistUser(userId);
        checkAccessAuthority(findUser, user);

        if(passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            findUser.emailUpdate(requestDto.getEmail());
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    /**
     * 존재하는 사용자인지 확인하는 메서드
     */
    private User checkToExistUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    /**
     * 접근 권한을 가지고 있는진 확인하는 메서드
     */
    private void checkAccessAuthority(User findUser, User user) {
        if(!findUser.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 계정에 대한 권한을 가지고 있지 않습니다.");
        }
    }
}
