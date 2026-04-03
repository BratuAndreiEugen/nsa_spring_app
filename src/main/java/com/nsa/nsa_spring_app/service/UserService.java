package com.nsa.nsa_spring_app.service;

import com.nsa.nsa_spring_app.config.security.CustomPasswordEncoder;
import com.nsa.nsa_spring_app.model.User;
import com.nsa.nsa_spring_app.model.enums.UserRole;
import com.nsa.nsa_spring_app.repository.UserRepository;
import com.nsa.nsa_spring_app.web.dto.UserCredentialsDto;
import com.nsa.nsa_spring_app.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor
@Service
public class UserService {
    private static final String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional("slaveTransactionManager")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional("masterTransactionManager")
    public UserDto saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserDto.builder()
                .id(savedUser.getId())
                .role(savedUser.getRole().getValue())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .build();
    }

    @Transactional("masterTransactionManager")
    public void updateUser(User user){
        userRepository.save(user);
    }

    @Transactional("masterTransactionManager")
    public void deleteUser(UUID id){
        userRepository.deleteById(id);
    }

    @Transactional("slaveTransactionManager")
    public UserDto getUserByCredentialsAndPassword(UserCredentialsDto userCredentialsDto){
        boolean isEmail = userCredentialsDto.getCredential().matches(emailRegex);
        User user;
        if(isEmail){
            user = userRepository.findByEmail(userCredentialsDto.getCredential());

        }
        else {
            user = userRepository.findByPhoneNumber(userCredentialsDto.getCredential());
        }

        if(user == null){
            throw new RuntimeException("User not found");
        }
        if(passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())){
            return UserDto.builder()
                    .id(user.getId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole().getValue())
                    .build();
        }
        else {
            throw new RuntimeException("Invalid password");
        }
    }
}
