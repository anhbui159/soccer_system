package com.abui.soccer_system.service;

import com.abui.soccer_system.model.BookedDetail;
import com.abui.soccer_system.model.SoccerField;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SoccerFieldService {
    Optional<SoccerField> findById(Long id);
    boolean checkForAvailability(LocalDateTime startTime, LocalDateTime endTime);
    boolean existsById(Long id);
    List<SoccerField> getFieldByBookedDetails(List<BookedDetail> bookedTicketDetailList);
    
}