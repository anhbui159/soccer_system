package com.abui.soccer_system.converter;

import com.abui.soccer_system.enums.EquipmentStatus;
import jakarta.persistence.*;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class EquipmentStatusConverter implements AttributeConverter<EquipmentStatus, String> {
    @Override
    public String convertToDatabaseColumn(EquipmentStatus equipmentStatus) {
        if(equipmentStatus == null) {
            return null;
        }
        return equipmentStatus.toString();
    }

    @Override
    public EquipmentStatus convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }
        return Stream.of(EquipmentStatus.values())
                .filter(str -> str.toString().equalsIgnoreCase(s))
                .findFirst().get();
    }
}
