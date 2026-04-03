package com.nsa.nsa_spring_app.model;

import com.nsa.nsa_spring_app.model.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(name="users")
@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private  String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

}
