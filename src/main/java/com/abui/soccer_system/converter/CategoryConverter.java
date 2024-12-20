package com.abui.soccer_system.converter;

import com.abui.soccer_system.enums.Category;
import jakarta.persistence.*;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter  implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category category) {
        if(category == null) {
            return null;
        }
        return category.toString();
    }

    @Override
    public Category convertToEntityAttribute(String s) {
        if(s == null) {
            return null;
        }
        return Stream.of(Category.values())
                .filter(str -> str.toString().equalsIgnoreCase(s))
                .findFirst().get();
    }
}
