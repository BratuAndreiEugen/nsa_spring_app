package com.nsa.nsa_spring_app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoggedUserDto {
    private String jwt;
    private String email;
    private String phoneNumber;
    private String fullName;
}
