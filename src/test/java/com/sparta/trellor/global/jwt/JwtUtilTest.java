package com.sparta.trellor.global.jwt;

import com.sparta.trellor.domain.user.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import com.sparta.trellor.test.CommonTest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.sparta.trellor.global.jwt.JwtUtil.BEARER_PREFIX;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilTest implements CommonTest {
    @Autowired
    JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        jwtUtil.init();
    }

    @DisplayName("토큰 생성")
    @Test
    void createToken() {
        // when
        String token = jwtUtil.createToken(TEST_USER_NAME, UserRoleEnum.USER);

        // then
        assertNotNull(token);
    }

    @DisplayName("토큰 추출")
    @Test
    void substringToken() {
        // given
        String token = "test-token";
        String bearerToken = BEARER_PREFIX + token;

        // when
        given(request.getHeader(JwtUtil.AUTHORIZATION_HEADER)).willReturn(bearerToken);
        String resolvedToken = jwtUtil.substringToken(bearerToken);

        // then
        assertEquals(token, resolvedToken);
    }

    @DisplayName("토큰 검증")
    @Nested
    class validateToken {
        @DisplayName("토큰 검증 성공")
        @Test
        void validateToken_success() {
            // given
            String bearerToken = jwtUtil.createToken(TEST_USER_NAME, UserRoleEnum.USER);
            String token = jwtUtil.substringToken(bearerToken);

            // when
            boolean isValid = jwtUtil.validateToken(token);

            // then
            assertTrue(isValid);
        }

        @DisplayName("토큰 검증 실패 - 유효하지 않은 토큰")
        @Test
        void validateToken_fail() {
            // given
            String token = "Invalid token";

            // when
            boolean isValid = jwtUtil.validateToken(token);

            // then
            assertFalse(isValid);
        }
    }

    @DisplayName("토큰에서 UserInfo 조회")
    @Test
    void getUserInfoFromToken() {
        // given
        String bearerToken = jwtUtil.createToken(TEST_USER_NAME, UserRoleEnum.USER);
        String token = jwtUtil.substringToken(bearerToken);

        // when
        Claims info = jwtUtil.getUserInfoFromToken(token);

        // then
        assertNotNull(info);
        assertEquals(TEST_USER_NAME, info.getSubject());
    }
}