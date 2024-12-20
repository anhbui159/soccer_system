package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="customer")
public class Customer extends CommonEntity{
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID")
    @RestResource(exported = false)
    private Account account;

    @Column(name = "REWARD_POINT", columnDefinition = "BIGINT DEFAULT 0")
    private Long rewardPoint;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private ZonedDateTime updatedAt;

    @Column(name = "IS_DELETED", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;
}
