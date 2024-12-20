package com.abui.soccer_system.model;

import com.abui.soccer_system.converter.OrderConverter;
import com.abui.soccer_system.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "booked")
public class Booked extends CommonEntity {
    @Column(name = "TOTAL_PRICE")
    private int totalPrice;

    @Column(name = "PAYMENT_STATUS")
    @Convert(converter = OrderConverter.class)
    private OrderStatus status;

    @Column(name = "NOTE")
    private String note;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    private Customer customer;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
    private Employee employee;

    @OneToMany(mappedBy = "booked", fetch = FetchType.LAZY)
    private List<BookedDetail> bookedDetails;
}