package com.abui.soccer_system.projection;

import com.abui.soccer_system.model.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "partialCustomer", types = {Customer.class})
public interface CustomerProjection {
    @Value("#{target.account.name}")
    String getFullName();

    @Value("#{target.account.gender}")
    String getSex();

    @Value("#{target.account.dob}")
    LocalDateTime getDob();

    @Value("#{target.account.address}")
    String getAddress();

    @Value("#{target.account.phone}")
    String getPhone();

    @Value("#{target.account.email}")
    String getEmail();

    @Value("#{target.account.username}")
    String getUsername();

    @Value("#{target.rewardPoint}")
    Long getRewardPoint();
}
