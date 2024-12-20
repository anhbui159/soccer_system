package com.abui.soccer_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String name;
    private int status;
    private String token;
    private List<String> roles;
    private String type = "Bearer";
}
