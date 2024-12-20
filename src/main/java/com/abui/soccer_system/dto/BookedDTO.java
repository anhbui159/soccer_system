package com.abui.soccer_system.dto;

import com.abui.soccer_system.enums.OrderStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookedDTO {
    private int totalPrice;

    private OrderStatus orderStatus;

    private String note;
}
