package com.abui.soccer_system.converter;

import com.abui.soccer_system.enums.Gender;
import jakarta.persistence.*;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, String>{
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if(gender == null) {
            return null;
        }
        return gender.toString();
    }

    @Override
    public Gender convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return Stream.of(Gender.values())
                .filter(str -> str.toString().equalsIgnoreCase(s))
                .findFirst().get();
    }
}
