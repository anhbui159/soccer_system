package com.abui.soccer_system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum RoleEnum {
    ADMIN ("ADMIN"),
    EMPLOYEE ("EMPLOYEE"),
    USER ("USER");
    private String name;

}