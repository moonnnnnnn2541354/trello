package com.sparta.trellor.global.jwt;


import com.sparta.trellor.domain.user.entity.User;
import com.sparta.trellor.global.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtil {

    public static Optional<User> getPrincipal() {
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(UserDetailsImpl.class::cast)
            .map(UserDetailsImpl::getUser);
    }
}
