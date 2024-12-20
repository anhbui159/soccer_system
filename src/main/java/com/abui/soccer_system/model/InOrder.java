package com.abui.soccer_system.model;


import com.abui.soccer_system.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="in_order")
public class InOrder extends CommonEntity{
    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS")
    private OrderStatus orderStatus;

    @Column(name = "NOTE")
    private String note;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;
}
