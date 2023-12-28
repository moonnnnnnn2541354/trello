package com.sparta.trellor.global.jwt;

import com.sparta.trellor.domain.user.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j(topic = "JwtUtil")
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";

    public static final String BEARER_PREFIX = "Bearer ";
    private final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    /**
     * 토큰 생성 메서드
     */
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /**
     * Jwt 토큰 쿠키에 넣는 메서드
     */
    public void addJwtToCookie(String token, HttpServletResponse response) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");

            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
            cookie.setPath("/");

            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 쿠키를 삭제하는 메서드
     */
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        String token = getTokenFromRequest(request);

        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, substringToken(token));
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * Jwt 토큰에서 토큰 식별자를 자르는 메서드
     */
    public String substringToken(String tokenValue) {
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    /**
     * 토큰 검증하는 메서드
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch(SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명입니다.");
        } catch(ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token입니다.");
        } catch(UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰입니다.");
        } catch(IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰입니다.");
        }
        return false;
    }

    /**
     * 토큰에서 사용자 정보를 가져오는 메서드
     */
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * HttpServletRequest에서 토큰을 가져오는 메서드
     */
    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                    try {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        return e.getMessage();
                    }
                }
            }
        }
        return null;
    }
}
