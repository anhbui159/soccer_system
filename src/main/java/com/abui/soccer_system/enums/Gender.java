package com.abui.soccer_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Gender {
    MALE,
    FEMALE,
    OTHER;
    private String type;
}