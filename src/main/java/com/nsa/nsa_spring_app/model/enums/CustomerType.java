package com.nsa.nsa_spring_app.model.enums;

import lombok.Getter;

@Getter
public enum CustomerType {
    NEW("NEW"),
    FREQUENT("FREQUENT"),
    INACTIVE("INACTIVE");

    private final String value;

    CustomerType(String value) {
        this.value = value;
    }
}
