package com.abui.soccer_system.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderExtra {
    @NotNull(message = "Must be valid ID")
    private Long itemID;
    @NotNull(message = "Please enter quantity")
    private int quantity;
    @NotNull(message = "Please enter delivery date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime delivery_date;
    @NotNull(message = "Please enter note")
    private String note;
}
