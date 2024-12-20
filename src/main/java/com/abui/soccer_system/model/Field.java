package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="field")
public class Field extends CommonEntity{
    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "OPENING_TIME")
    private LocalTime openTime;

    @Column(name = "CLOSING_TIME")
    private LocalTime closeTime;

    @Column(name = "FEE")
    private double Fee;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SoccerField> soccerFields;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> users;
}
