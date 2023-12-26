package com.sparta.trellor.domain.user.service;

import com.sparta.trellor.domain.user.dto.request.SignupRequestDto;
import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> user = userRepository.findByUsernameAndEmail(username, email);

        if(user.isPresent()) {

        }
    }
}
