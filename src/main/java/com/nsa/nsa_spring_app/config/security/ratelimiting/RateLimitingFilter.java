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
public class RateLimitingFilter extends OncePerRequestFilter {

    private final StringRedisTemplate redisTemplate;

    @Value("${jwt.auth-header}")
    private String authHeader;

    @Value("${rate-limit.max-requests-per-minute}")
    private int maxRequestsPerMinute;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(authHeader);

        if(token != null && !token.isEmpty()) {
            String key = "rate_limit:jwt:" + token;

            Long count = redisTemplate.opsForValue().increment(key);
            if(count != null && count == 1L) {
                redisTemplate.expire(key, Duration.ofMinutes(1));
            }
            if(count != null && count > maxRequestsPerMinute) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too many requests");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
