package com.nsa.nsa_spring_app.config.security.jwt;

import com.nsa.nsa_spring_app.web.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;

@Service
@Slf4j
public class JWTService {

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.auth-header}")
    private String authHeader;

    @Value("${jwt.secret}")
    private String secret;

    public Authentication getAuthentication(HttpServletRequest request){
        final String token = request.getHeader(authHeader);
        if(token == null || token.isEmpty()) {
            log.warn("No jwt token found in request headers");
            return null;
        }

        try{
            Jws<Claims> claims = parseToken(token);

            String email = claims.getPayload().getSubject();
            String role = Optional.ofNullable(claims.getPayload().get("role")).map(Object::toString).orElseThrow(() ->
                    new AuthenticationCredentialsNotFoundException("No role found in jwt"));
            String phoneNumber = Optional.ofNullable(claims.getPayload().get("phone_number")).map(Object::toString).orElseThrow(() ->
                    new AuthenticationCredentialsNotFoundException("No phone number found in jwt"));

            return createAuthentication(email, role, phoneNumber);
        } catch (Exception e){
            log.error("[JWTService getAuthentication()] {}", e.getMessage());
            throw e;
        }
    }

    public Authentication createAuthentication(String email, String role, String phoneNumber){
        role = role.replaceAll("[\\[\\]]", "");
        List<GrantedAuthority> grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(role));

        Map<String, Object> principal = Map.of(
                "email", email,
                "phone_number", phoneNumber
        );

        return new UsernamePasswordAuthenticationToken(principal, null, grantedAuthorities);
    }

    public String createJWTToken(UserDto userDto){
        return Jwts.builder()
                .subject(userDto.getEmail())
                .claim("role", userDto.getRole())
                .claim("phone_number", userDto.getPhoneNumber())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Duration.ofHours(expiration).toMillis()))
                .signWith(getSecretKey())
                .compact();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }



}
