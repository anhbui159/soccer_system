package com.abui.soccer_system.model;


import com.abui.soccer_system.converter.CategoryConverter;
import com.abui.soccer_system.converter.EquipmentStatusConverter;
import com.abui.soccer_system.enums.Category;
import com.abui.soccer_system.enums.EquipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="item")
public class Item extends CommonEntity{
    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    @Convert(converter = EquipmentStatusConverter.class)
    private EquipmentStatus status;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "IMAGE")
    @Lob
    private String image;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CATEGORY")
    @Convert(converter = CategoryConverter.class)
    private Category itemCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SOURCE_ID", referencedColumnName = "ID")
    private Source source;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<OutOrderDetail> outOrderDetails;
}
