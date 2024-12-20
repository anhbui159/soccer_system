package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="service")
public class Service extends CommonEntity{
    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private int price;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "service_item", joinColumns = @JoinColumn(name =
            "SERVICE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID", referencedColumnName = "ID"))
    private Set<Item> items = new HashSet<>();
}
