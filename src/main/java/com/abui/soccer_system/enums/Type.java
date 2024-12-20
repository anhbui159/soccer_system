package com.abui.soccer_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum Type {
    FIELD_1,
    FIELD_2,
    FIELD_3;
    private String type;

    private Type(){}

    private Type(String type) {
        this.type = type;
    }
}