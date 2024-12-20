package com.abui.soccer_system.converter;

import com.abui.soccer_system.enums.Type;
import jakarta.persistence.*;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, String>{
    @Override
    public String convertToDatabaseColumn(Type type) {
//        System.out.println(type);
        if(type == null) {
            return null;
        }
        return type.toString();
    }

    @Override
    public Type convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }
        return Stream.of(Type.values())
                .filter(str -> str.toString().equalsIgnoreCase(s))
                .findFirst().get();
    }

}
