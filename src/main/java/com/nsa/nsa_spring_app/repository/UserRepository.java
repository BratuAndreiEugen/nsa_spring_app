package com.nsa.nsa_spring_app.repository;

import com.nsa.nsa_spring_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmailAndPassword(String email, String password);
    User findByPhoneNumberAndPassword(String phoneNumber, String password);
    User findByEmail(String email);
    User findByPhoneNumber(String phoneNumber);
}
