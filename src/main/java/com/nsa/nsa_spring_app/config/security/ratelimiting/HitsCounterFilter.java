package com.nsa.nsa_spring_app.config.security.ratelimiting;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class HitsCounterFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;

    private final String instanceId = System.getenv("HOSTNAME") != null ?
            System.getenv("HOSTNAME") : "unknown-instance";

    @Value("${jwt.auth-header}")
    private String authHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(authHeader);

        if(token != null && !token.isEmpty()) {
            String key = "hits:jwt:" + token + ":instance:" + instanceId;

            redisTemplate.opsForValue().increment(key);
            redisTemplate.expire(key, Duration.ofMinutes(5));
        }
        String key = "hits:instance:" + instanceId;

        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofMinutes(5));

        filterChain.doFilter(request, response);
    }
}
