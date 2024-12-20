package com.abui.soccer_system.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="booked_detail")
public class BookedDetail{
    @EmbeddedId
    private BookedDetailKey id;

    @JoinColumn(name = "BOOKED_ID")
    @MapsId("bookedId")
    @ManyToOne
    private Booked booked;

    @JoinColumn(name = "SOCCER_FIELD_ID")
    @MapsId("soccerFieldId")
    @ManyToOne
    private SoccerField soccerField;

    @Column(name = "STARTING_TIME")
    private LocalDateTime startingTime;

    @Column(name = "ENDING_TIME")
    private LocalDateTime endingTime;

    @Column(name = "DEPOSIT")
    private int deposit;

    @Column(name = "ORDER_DATE")
    @CreationTimestamp
    private ZonedDateTime orderDate;

    @Column(name = "IS_CANCELED", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isCanceled;

}
