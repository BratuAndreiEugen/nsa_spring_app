package com.nsa.nsa_spring_app.config.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder delegateEncoder = new BCryptPasswordEncoder();

    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        return delegateEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isEmpty()) {
            return false;
        }
        return delegateEncoder.matches(rawPassword, encodedPassword);
    }
}
