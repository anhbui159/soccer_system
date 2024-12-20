package com.abui.soccer_system.projection;

import com.abui.soccer_system.model.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "partialAccount", types = {Account.class})
public interface AccountProjection {
    @Value("#{target.username}")
    String getUsername();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.gender}")
    String getGender();

    @Value("#{target.dob}")
    LocalDateTime getDob();

    @Value("#{target.address}")
    String getAddress();

    @Value("#{target.phone}")
    String getPhone();

    @Value("#{target.email}")
    String getEmail();
}
