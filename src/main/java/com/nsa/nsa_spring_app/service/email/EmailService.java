package com.nsa.nsa_spring_app.service.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.address}")
    private String address;

    private final JavaMailSender mailSender;

    @Async
    public void sendRegistrationEmail(String toEmail, String fullName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(address);
        message.setTo(toEmail);
        message.setSubject("Welcome to NSA App!");
        message.setText("Hello " + fullName + ",\n\nYour registration was successful!");

        mailSender.send(message);
    }
}
