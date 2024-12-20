package com.abui.soccer_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum OrderStatus {
    SUCCEEDED,
    PROCESSING,
    FAILED;
}