package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Booked;
import com.abui.soccer_system.model.BookedDetail;
import com.abui.soccer_system.repository.BookedDetailRepository;
import com.abui.soccer_system.repository.BookedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookedServiceImplementation implements BookedService {
    private final BookedRepository bookedRepository;

    private final BookedDetailRepository bookedDetailRepository;

    @Override
    public Booked save(Booked booked) {
        return bookedRepository.save(booked);
    }

    @Override
    public BookedDetail save(BookedDetail BookedDetail) {
        return bookedDetailRepository.save(BookedDetail);
    }

    @Override
    public List<BookedDetail> getPastBookedDetailByDate(LocalDateTime dateTime) {
        List<BookedDetail> BookedDetailList = bookedDetailRepository.findAll();

        for (BookedDetail btd:BookedDetailList) {
            LocalDate date = btd.getStartingTime().toLocalDate();
            LocalDate checkDate = dateTime.toLocalDate();

            if (!date.isEqual(checkDate)) {
                BookedDetailList.remove(btd);
                continue;
            }
            if (!btd.getEndingTime().isBefore(dateTime)) {
                BookedDetailList.remove(btd);
            }
        }

        return BookedDetailList;
    }

    @Override
    public List<BookedDetail> getCurrentBookedDetailByDate(LocalDateTime dateTime) {
        List<BookedDetail> BookedDetailList = new ArrayList<>();

        for (BookedDetail btd:bookedDetailRepository.findAll()) {
            LocalDate date = btd.getStartingTime().toLocalDate();
            LocalDate checkDate = dateTime.toLocalDate();

            if (!date.isEqual(checkDate)) {
                continue;
            }

            if (btd.getStartingTime().isBefore(dateTime) && btd.getEndingTime().isAfter(dateTime)) {
                BookedDetailList.add(btd);
            }
        }

        return BookedDetailList;
    }

    @Override
    public List<BookedDetail> getFutureBookedDetailByDate(LocalDateTime dateTime) {
        List<BookedDetail> BookedDetailList = new ArrayList<>();

        for (BookedDetail btd:bookedDetailRepository.findAll()) {
            LocalDate date = btd.getStartingTime().toLocalDate();
            LocalDate checkDate = dateTime.toLocalDate();

            if (!date.isEqual(checkDate)) {
                continue;
            }

            if (btd.getStartingTime().isAfter(dateTime)) {
                BookedDetailList.add(btd);
            }
        }

        return BookedDetailList;
    }

    @Override
    public List<BookedDetail> getBookedDetailByWeek(LocalDateTime firstDay, LocalDateTime lastDay, Long field_id) {
        List<BookedDetail> BookedDetailList = new ArrayList<>();

        for (BookedDetail btd : bookedDetailRepository.findAll()) {
            if (btd.getSoccerField().getId() == field_id && btd.getStartingTime().isAfter(firstDay) && btd.getStartingTime().isBefore(lastDay) && !btd.isCanceled()) {
                BookedDetailList.add(btd);
            }
        }
        return BookedDetailList;
    }

    @Override
    public List<Booked> getBookedByQuarter(int year, int quarter) {
        List<Booked> BookedList = new ArrayList<>();

        for (Booked receipt: bookedRepository.findAll()) {
            if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().get(IsoFields.QUARTER_OF_YEAR) == quarter) {
                BookedList.add(receipt);
            }
        }

        return BookedList;
    }

    @Override
    public int getBookedPriceByMonth(int month, int year) {
        List<Booked> BookedList = new ArrayList<>();

        for (Booked receipt: bookedRepository.findAll()) {
            if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().getMonthValue() == month) {
                BookedList.add(receipt);
            }
        }

        int totalPrice = 0;

        for (Booked receipt:BookedList) {
            boolean isNotCancel =
                    receipt.getBookedDetails()
                            .stream().filter(BookedDetail -> BookedDetail.isCanceled())
                            .collect(Collectors.toList()).isEmpty();
            if(isNotCancel) {
                totalPrice += receipt.getTotalPrice();
            }
        }

        return totalPrice;
    }

    @Override
    public int getBookedPriceByDay(int day, int month, int year) {
        List<Booked> BookedList = new ArrayList<>();

        for (Booked receipt: bookedRepository.findAll()) {
            if (receipt.getCreatedAt().getYear() == year && receipt.getCreatedAt().getMonthValue() == month && receipt.getCreatedAt().getDayOfMonth() == day) {
                BookedList.add(receipt);
            }
        }

        int totalPrice = 0;

        for (Booked receipt:BookedList) {
            totalPrice += receipt.getTotalPrice();
        }

        return totalPrice;
    }
}