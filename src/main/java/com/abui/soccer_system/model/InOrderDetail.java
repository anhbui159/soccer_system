package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="in_order_detail")
public class InOrderDetail {
    @EmbeddedId
    private InOrderDetailKey id;

    @JoinColumn(name = "IN_ORDER_ID")
    @MapsId("inOrderId")
    @ManyToOne
    private InOrder inOrder;

    @JoinColumn(name = "ITEM_ID")
    @MapsId("itemId")
    @ManyToOne
    private Item item;

    @Column(name = "DELIVERY_DATE")
    private LocalDateTime deliveryDate;

    @CreationTimestamp
    @Column(name = "ORDER_DATE")
    private ZonedDateTime orderDate;

    @Column(name = "QUANTITY")
    private int quantity;
}
