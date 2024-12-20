package com.abui.soccer_system.projection;

import com.abui.soccer_system.model.Booked;
import com.abui.soccer_system.model.BookedDetail;
import com.abui.soccer_system.model.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;

@Projection(name = "partialBooked", types = {BookedDetail.class})
public interface BookDetailProjection {
    @Value("#{target.field}")
    Field getField();

    @Value("#{target.startingTime}")
    LocalDateTime getStartingTime();

    @Value("#{target.endingTime}")
    LocalDateTime getEndingTime();

    @Value("#{target.booked}")
    Booked getBooked();
}
