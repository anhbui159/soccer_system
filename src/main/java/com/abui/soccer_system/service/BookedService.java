package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Booked;
import com.abui.soccer_system.model.BookedDetail;

import java.time.LocalDateTime;
import java.util.List;

public interface BookedService {
    Booked save(Booked bookedTicket);

    BookedDetail save(BookedDetail bookedTicketDetail);

    List<BookedDetail> getPastBookedDetailByDate(LocalDateTime dateTime);

    List<BookedDetail> getCurrentBookedDetailByDate(LocalDateTime dateTime);

    List<BookedDetail> getFutureBookedDetailByDate(LocalDateTime dateTime);

    List<BookedDetail> getBookedDetailByWeek(LocalDateTime firstDay, LocalDateTime lastDay, Long field_id);

    List<Booked> getBookedByQuarter(int year, int quarter);

    int getBookedPriceByMonth(int month, int year);

    int getBookedPriceByDay(int day, int month, int year);
}