package com.abui.soccer_system.dto;

import lombok.*;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {
    private ZonedDateTime createdAt;

    private String type;

    private OrderHistoryDTO booked;

    private OrderHistoryDTO outOrder;

    private OrderHistoryDTO inOrder;
}
