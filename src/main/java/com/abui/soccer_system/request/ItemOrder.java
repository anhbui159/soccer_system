package com.abui.soccer_system.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ItemOrder {
    private String phone;

    @NotNull(message = "Items must not be null")
    private List<HashMap<String, Long>> items;

}