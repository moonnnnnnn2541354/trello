package com.sparta.trellor.domain.user.service;

import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.user.entity.UserRoleEnum;
import com.sparta.trellor.domain.user.repository.UserRepository;
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
}
