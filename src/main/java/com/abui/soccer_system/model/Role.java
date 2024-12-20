package com.abui.soccer_system.model;

import com.abui.soccer_system.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "role")
public class Role {
    @Id
    @Column(nullable = false, updatable = false, name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NaturalId
    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}