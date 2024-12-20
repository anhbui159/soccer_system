package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="employee")
public class Employee extends CommonEntity{
    @OneToOne(fetch =  FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    private Account account;

    @Column(name = "CARD")
    private String card;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FIELD_GROUP_ID", referencedColumnName = "ID")
    private Field field;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SALARY", columnDefinition = "DOUBLE DEFAULT 0")
    private double salary;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private ZonedDateTime updatedAt;

    @Column(name = "IS_DELETED", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;
}
