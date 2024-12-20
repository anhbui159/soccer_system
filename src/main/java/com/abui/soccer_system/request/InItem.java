package com.abui.soccer_system.request;

import com.abui.soccer_system.enums.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
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
public class InItem {
    @NotNull(message = "Source id must not null")
    private Long source_id;

    @NotNull(message = "Image must not null")
    private String image;

    @NotNull(message = "Import price must not null")
    @Min(value = 0, message = "Import price must greater or equal 0")
    private int inPrice;

    @NotNull(message = "Item category must not null")
    private Category category;

    @NotNull(message = "Item's name must not null")
    private String name;

    private String note;

    @NotNull(message = "Quantity must not null")
    @Min(value = 0, message = "Quantity must greater or equal 0")
    private int quantity;

    @NotNull(message = "Item unit must not null")
    private String unit;

    @NotNull(message = "Please enter delivery date")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime delivery_date;
}
