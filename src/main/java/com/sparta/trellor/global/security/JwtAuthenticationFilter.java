package com.sparta.trellor.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.trellor.domain.user.dto.request.LoginRequestDto;
import com.sparta.trellor.domain.user.entity.UserRoleEnum;
import com.sparta.trellor.global.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/users/login");
    }

    /**
     * 로그인을 시도하는 메서드
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        log.info("로그인 시도");
        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch(IOException e) {
            log.error("로그인 실패 : " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 인증 성공시 실행되는 메서드
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) {
        log.info("로그인 성공");

        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        String token = jwtUtil.createToken(username, role);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    /**
     * 인증 실패시 실행되는 메서드
     */
    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        log.info("로그인 실패");

        response.setStatus(401);
        response.setContentType("application/json;charset=uft-8");
        response.getWriter().write("닉네임 또는 비밀번호를 확인해주세요");
    }
}
