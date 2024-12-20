package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class InOrderDetailKey implements Serializable {
    @Column(name = "IN_ORDER_ID")
    private Long inOrderId;

    @Column(name = "ITEM_ID")
    private Long itemId;
}
