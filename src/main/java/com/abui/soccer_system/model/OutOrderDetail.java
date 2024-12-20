package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="out_order_detail")
public class OutOrderDetail {
    @EmbeddedId
    private OutOrderDetailKey id;

    @JoinColumn(name = "ITEM_ID")
    @MapsId("itemId")
    @ManyToOne
    private Item item;

    @JoinColumn(name = "SERVICE_RECEIPT_ID")
    @MapsId("outOrderId")
    @ManyToOne
    private OutOrder outOrder;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "ORDER_DATE")
    @CreationTimestamp
    private ZonedDateTime orderDate;
}
