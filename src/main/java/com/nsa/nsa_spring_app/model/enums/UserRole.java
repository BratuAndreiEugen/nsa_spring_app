package com.nsa.nsa_spring_app.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    ACCOUNTANT("ACCOUNTANT"),
    EXTERNAL("EXTERNAL");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }
}
