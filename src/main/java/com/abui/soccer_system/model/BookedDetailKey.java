package com.abui.soccer_system.model;


import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookedDetailKey implements Serializable {
    @Column(name="SOCCER_FIELD_ID")
    private Long soccerFieldId;

    @Column(name = "BOOKED_ID")
    private Long bookedId;
}
