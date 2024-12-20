package com.abui.soccer_system.model;

import com.abui.soccer_system.converter.TypeConverter;
import com.abui.soccer_system.enums.Type;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="soccer_field")
public class SoccerField extends CommonEntity{
    @Column(name="NAME")
    private String name;

    @Column(name="TYPE")
    @Convert(converter = TypeConverter.class)
    private Type type;

    @Column(name = "IMAGE")
    @Lob
    private String image;

    @Column(name = "PRICE" , columnDefinition = "DOUBLE DEFAULT 0")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FIELD_ID", referencedColumnName = "ID")
    private Field field;
}
