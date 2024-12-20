package com.abui.soccer_system.request;

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
public class SignIn {
    @NotNull(message = "Please enter your username")
    @NotEmpty(message = "Please enter your username")
    private String username;

    @NotNull(message = "Please enter your password")
    @NotEmpty(message = "Please enter your password")
    private String password;
}
