package com.abui.soccer_system.dto;

import com.abui.soccer_system.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Convert;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class CustomerDTO {
    private String name;

    @Convert(converter = Gender.class)
    private Gender gender;

    private String email;

    private String address;

    private String phone;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime dob;

    private Long rewardPoint;
}
