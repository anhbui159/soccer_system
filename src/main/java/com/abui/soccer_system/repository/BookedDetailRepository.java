package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.BookedDetail;
import com.abui.soccer_system.model.Booked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(excerptProjection = Booked.class)
public interface BookedDetailRepository extends JpaRepository<BookedDetail, Long> {
    List<BookedDetail> findByStartingTimeGreaterThanAndStartingTimeLessThanOrEndingTimeGreaterThanAndEndingTimeLessThan(LocalDateTime startTime, LocalDateTime endTime, LocalDateTime startTimeEnd, LocalDateTime endTimeEnd);
    Optional<BookedDetail> findByBookedId(Long id);
}