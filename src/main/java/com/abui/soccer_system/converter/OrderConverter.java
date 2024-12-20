package com.abui.soccer_system.converter;

import com.abui.soccer_system.enums.OrderStatus;
import jakarta.persistence.*;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderConverter implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        if(orderStatus == null) {
            return null;
        }
        return orderStatus.toString();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }
        return Stream.of(OrderStatus.values())
                .filter(str -> str.toString().equalsIgnoreCase(s))
                .findFirst().get();
    }
}
