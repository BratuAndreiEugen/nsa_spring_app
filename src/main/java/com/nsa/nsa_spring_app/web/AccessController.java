package com.nsa.nsa_spring_app.web;

import com.nsa.nsa_spring_app.config.security.annot.AllowAdminOnly;
import com.nsa.nsa_spring_app.config.security.jwt.JWTService;
import com.nsa.nsa_spring_app.model.User;
import com.nsa.nsa_spring_app.model.enums.UserRole;
import com.nsa.nsa_spring_app.service.UserService;
import com.nsa.nsa_spring_app.service.email.EmailService;
import com.nsa.nsa_spring_app.web.dto.LoggedUserDto;
import com.nsa.nsa_spring_app.web.dto.RegisterDto;
import com.nsa.nsa_spring_app.web.dto.UserCredentialsDto;
import com.nsa.nsa_spring_app.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/access")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AccessController {
    private final UserService userService;
    private final JWTService jwtService;
    private final EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentialsDto userCredentialsDto){
        try {
            UserDto userDto = userService.getUserByCredentialsAndPassword(userCredentialsDto);
            String jwt = jwtService.createJWTToken(userDto);
            return ResponseEntity.ok(LoggedUserDto.builder()
                    .email(userDto.getEmail())
                    .fullName(userDto.getFullName())
                    .phoneNumber(userDto.getPhoneNumber())
                    .jwt(jwt)
                    .id(userDto.getId())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        try{
            UserDto userDto = userService.saveUser(User.builder()
                    .fullName(registerDto.getFullName())
                    .email(registerDto.getEmail())
                    .phoneNumber(registerDto.getPhoneNumber())
                    .password(registerDto.getPassword())
                    .role(UserRole.valueOf(registerDto.getRole()))
                    .build());

            emailService.sendRegistrationEmail(userDto.getEmail(), userDto.getFullName());

            String jwt = jwtService.createJWTToken(userDto);
            return ResponseEntity.ok(LoggedUserDto.builder()
                    .email(userDto.getEmail())
                    .fullName(userDto.getFullName())
                    .phoneNumber(userDto.getPhoneNumber())
                    .jwt(jwt)
                    .id(userDto.getId())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @AllowAdminOnly
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
