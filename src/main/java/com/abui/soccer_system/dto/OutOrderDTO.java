package com.abui.soccer_system.dto;

import com.abui.soccer_system.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutOrderDTO {
    private int totalPrice;

    private OrderStatus orderStatus;

    private String note;
}
