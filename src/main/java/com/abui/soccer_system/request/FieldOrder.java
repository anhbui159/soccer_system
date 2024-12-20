package com.abui.soccer_system.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldOrder {
    @NotNull(message = "Invalid field ID")
    private Long field_id;

    @NotNull(message = "Invalid phone number")
    @Length(min = 10, max = 10 , message = "Phone number must be 10 characters")
    private String phone;

    @NotNull(message = "Please enter starting time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startingTime;
    @NotNull(message = "Please enter ending time")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endingTime;
}
