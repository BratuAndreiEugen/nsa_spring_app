package com.nsa.nsa_spring_app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggedUserDto {
    private String jwt;
    private UUID id;
    private String email;
    private String phoneNumber;
    private String fullName;
}
