package com.nsa.nsa_spring_app.model.enums;

import lombok.Getter;

@Getter
public enum PaymentType {
    CASH("CASH"),
    CARD("CARD");

    private final String value;

    PaymentType(String value) {
        this.value = value;
    }
}
