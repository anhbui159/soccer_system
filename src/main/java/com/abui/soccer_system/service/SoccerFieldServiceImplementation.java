package com.abui.soccer_system.service;

import com.abui.soccer_system.model.BookedDetail;
import com.abui.soccer_system.model.SoccerField;
import com.abui.soccer_system.repository.BookedDetailRepository;
import com.abui.soccer_system.repository.SoccerFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoccerFieldServiceImplementation implements SoccerFieldService {

    private final SoccerFieldRepository soccerFieldRepository;


    private final BookedDetailRepository bookedDetailRepository;

    @Override
    public boolean existsById(Long id) {
        return soccerFieldRepository.existsById(id);
    }

    @Override
    public boolean checkForAvailability(LocalDateTime startTime,
                                        LocalDateTime endTime) {
        List<BookedDetail> isBooked =
                bookedDetailRepository.findByStartingTimeGreaterThanAndStartingTimeLessThanOrEndingTimeGreaterThanAndEndingTimeLessThan(startTime, endTime, startTime, endTime);
        if (!isBooked.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<SoccerField> findById(Long id) {
        return soccerFieldRepository.findById(id);
    }

    @Override
    public List<SoccerField> getFieldByBookedDetails(List<BookedDetail> bookedDetailList) {
        List<SoccerField> soccerFieldList = new ArrayList<>();

        for (BookedDetail bd:bookedDetailList) {
            soccerFieldList.add(bd.getSoccerField());
        }

        return soccerFieldList;
    }


}