package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
public class CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private ZonedDateTime createdAt;
}
