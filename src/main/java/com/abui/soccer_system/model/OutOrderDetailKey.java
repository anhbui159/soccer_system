package com.abui.soccer_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OutOrderDetailKey implements Serializable {
    @Column(name = "ITEM_ID")
    private Long itemId;

    @Column(name = "OUT_ORDER_ID")
    private Long outOrderId;
}
