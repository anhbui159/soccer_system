package com.abui.soccer_system.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO{
    @NotNull(message = "Username cant be null")
    @NotEmpty(message = "Username cant be empty")
    private String username;
}
