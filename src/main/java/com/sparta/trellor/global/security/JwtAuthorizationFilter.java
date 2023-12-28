package com.sparta.trellor.global.security;

import com.sparta.trellor.global.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "jwt 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String tokenValue = jwtUtil.getTokenFromRequest(request);
        String url = request.getRequestURI();

        if(url.startsWith("/api/users/signup") || url.startsWith("/api/users/login")) {
            filterChain.doFilter(request, response);
        } else {
            log.info(tokenValue);
            if(tokenValue == null) {
                doValidateToken(tokenValue);
            }

            if(StringUtils.hasText(tokenValue)) {
                tokenValue = jwtUtil.substringToken(tokenValue);
                doValidateToken(tokenValue);
                Claims info = jwtUtil.getUserInfoFromToken(tokenValue);

                try {
                    setAuthentication(info.getSubject());
                } catch(Exception e) {
                    log.error(e.getMessage());
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 토큰 검증 메서드를 호출하는 메서드
     */
    private void doValidateToken(String tokenValue) {
        if(!jwtUtil.validateToken(tokenValue)) {
            log.error("Token Error");
        }
    }

    /**
     * 인증처리하는 메서드
     */
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    /**
     * 인증 객체 생성
     */
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
