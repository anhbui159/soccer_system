package com.abui.soccer_system.model;

import com.abui.soccer_system.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="out_order")
public class OutOrder extends CommonEntity{
    @Column(name = "PAYMENT_STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @Column(name = "NOTE")
    private String note;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private Customer user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

    @OneToMany(mappedBy = "outOrder", fetch = FetchType.LAZY)
    private List<OutOrderDetail> outOrderDetails;
}
