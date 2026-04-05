package com.nsa.nsa_spring_app.config.security;

import com.nsa.nsa_spring_app.config.security.jwt.JWTAuthEntryPoint;
import com.nsa.nsa_spring_app.config.security.jwt.JWTFilter;
import com.nsa.nsa_spring_app.config.security.ratelimiting.HitsCounterFilter;
import com.nsa.nsa_spring_app.config.security.ratelimiting.RateLimitingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http, RateLimitingFilter rlFilter, HitsCounterFilter hitsCounterFilter) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/healthcheck/ping").permitAll()
                .requestMatchers("/healthcheck/hello").permitAll()
                .requestMatchers("/access/login").permitAll()
                .requestMatchers("/access/register").permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint))
                .addFilterBefore(hitsCounterFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(rlFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), BasicAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public CustomPasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter();
    }

    @Bean
    public RateLimitingFilter rlFilter(StringRedisTemplate redisTemplate) {
        return new RateLimitingFilter(redisTemplate);
    }

    @Bean
    public HitsCounterFilter hitsCounterFilter(StringRedisTemplate redisTemplate) {
        return new HitsCounterFilter(redisTemplate);
    }

}
