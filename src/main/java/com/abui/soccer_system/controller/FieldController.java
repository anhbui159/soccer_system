package com.abui.soccer_system.controller;

import com.abui.soccer_system.dto.Message;
import com.abui.soccer_system.enums.OrderStatus;
import com.abui.soccer_system.model.*;
import com.abui.soccer_system.repository.BookedDetailRepository;
import com.abui.soccer_system.repository.EmployeeRepository;
import com.abui.soccer_system.request.FieldOrder;
import com.abui.soccer_system.security.UserPrincipal;
import com.abui.soccer_system.service.BookedService;
import com.abui.soccer_system.service.CustomerService;
import com.abui.soccer_system.service.SoccerFieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FieldController {
    private final SoccerFieldService soccerFieldService;

    private final CustomerService customerService;

    private final BookedService bookedService;

    private final EmployeeRepository employeeRepository;

    private final BookedDetailRepository bookedDetailRepository;

    @PostMapping("/fields/order")
    public ResponseEntity<?> orderField(@Valid @RequestBody FieldOrder fieldOrder) {
//        System.out.println(fieldOrder);
        if (!soccerFieldService.existsById(fieldOrder.getField_id())) {
            return new ResponseEntity<>(new Message("The field does not exist", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        if (!customerService.existsByPhoneNumber(fieldOrder.getPhone())) {
            return new ResponseEntity<>(new Message("The customer does not exist", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        if (soccerFieldService.checkForAvailability(fieldOrder.getStartingTime(), fieldOrder.getEndingTime())) {
            return new ResponseEntity<>(new Message("The field is ordered",
                    HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        int hours =
                fieldOrder.getEndingTime().getHour() - fieldOrder.getStartingTime().getHour();
        int minutes =
                fieldOrder.getEndingTime().getMinute() - fieldOrder.getStartingTime().getMinute();
        if(hours * 60 + minutes < 60) {
            return new ResponseEntity<>(new Message("The field must be " +
                    "ordered at least 60 minutes",
                    HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        LocalDateTime now = LocalDateTime.now();
        boolean isStartTimeBeforeNow = fieldOrder.getStartingTime().isBefore(now);
        boolean isEndTimeBeforeNow = fieldOrder.getEndingTime().isBefore(now);
        if(isStartTimeBeforeNow || isEndTimeBeforeNow) {
            return new ResponseEntity<>(new Message("Please choose the next" +
                    " time from now",
                    HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        Long fieldId = fieldOrder.getField_id();

        SoccerField soccerField =
                soccerFieldService.findById(fieldId).get();

        int hourStartTime = fieldOrder.getStartingTime().getHour();
        boolean hasFieldFeeWeight =
                hourStartTime >= 18 && hourStartTime <= 22 || hourStartTime >= 5 && hourStartTime <= 8;
//        System.out.println(hasFieldFeeWeight);
        double fieldFeeWeight = 0;
        int totalPrice = (int) Math.floor(soccerField.getPrice() * (hours + minutes / 60));
        if(hasFieldFeeWeight) {
            fieldFeeWeight = 0.2;
            totalPrice = (int) Math.floor(totalPrice + totalPrice * fieldFeeWeight);
        }

        Customer customer =
                customerService.findCustomerByPhone(fieldOrder.getPhone()).get();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long currentAccountId = userPrincipal.getId();

        Employee employee = null;

        Optional<Employee> optionalEmployee =
                employeeRepository.findEmployeeByAccountId(currentAccountId);

        if (optionalEmployee.isPresent() && !optionalEmployee.isEmpty()) {
            employee = employeeRepository.findEmployeeByAccountId(currentAccountId).get();
        }

        Booked booked =
                Booked.builder().customer(customer).employee(employee).status(OrderStatus.PROCESSING).totalPrice(totalPrice).build();

        BookedDetail bookedDetail =
                BookedDetail.builder()
                        .id(BookedDetailKey.builder().bookedId(booked.getId()).soccerFieldId(fieldId).build())
                        .booked(booked)
                        .soccerField(soccerField)
                        .deposit(0)
                        .startingTime(fieldOrder.getStartingTime())
                        .endingTime(fieldOrder.getEndingTime())
                        .build();

        Booked bookedBody = bookedService.save(booked);
        BookedDetail bookedDetailBody = bookedService.save(bookedDetail);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("receipt_id", bookedBody.getId());
        response.put("total_price", totalPrice);
        response.put("payment_status", OrderStatus.PROCESSING.toString());
        response.put("created_at", bookedBody.getCreatedAt());
        response.put("deposit", bookedDetailBody.getDeposit());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/booked/played")
    public ResponseEntity<?> getPlayedBookedFields(@Valid @RequestBody com.abui.soccer_system.request.Booked bookedForm) {

        LocalDateTime bookedDate = LocalDateTime.now();
        List<BookedDetail> bookedTicketDetailList =
                bookedService.getPastBookedDetailByDate(bookedDate);

        List<SoccerField> soccerFieldList =
                soccerFieldService.getFieldByBookedDetails(bookedTicketDetailList);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("fieldList", soccerFieldList);
        response.put("soccer_status", "played");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/booked/playing")
    public ResponseEntity<?> getPlayingBookedFields(@Valid @RequestBody com.abui.soccer_system.request.Booked bookedForm) {
        LocalDateTime bookedDate = LocalDateTime.now();
        List<BookedDetail> bookedDetailList =
                bookedService.getCurrentBookedDetailByDate(bookedDate);

        List<SoccerField> footballFieldList =
                soccerFieldService.getFieldByBookedDetails(bookedDetailList);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("fieldList", footballFieldList);
        response.put("football_status", "playing");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/booked/will-play")
    public ResponseEntity<?> getWillPlayBookedFields(@Valid @RequestBody com.abui.soccer_system.request.Booked bookedForm) {
//		LocalDateTime bookedDate = bookedTicketForm.getBooked_date();
        LocalDateTime bookedDate = LocalDateTime.now();
        List<BookedDetail> bookedTicketDetailList =
                bookedService.getFutureBookedDetailByDate(bookedDate);

        List<SoccerField> footballFieldList =
                soccerFieldService.getFieldByBookedDetails(bookedTicketDetailList);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("fieldList", footballFieldList);
        response.put("football_status", "will-play");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/booked/week")
    public ResponseEntity<?> getBookedFieldByWeek(@Valid @RequestBody com.abui.soccer_system.request.Booked bookedForm) {
        Long field_id = bookedForm.getField_id();

        LocalDate currentDay = LocalDate.now();

        int dayOfWeek = currentDay.getDayOfWeek().getValue();

        Map<Integer, Integer> days = getDaysOfWeek();

        int neededDay = days.get(DayOfWeek.SUNDAY.getValue()) - days.get(dayOfWeek);

        LocalDateTime firstDayOfWeek = currentDay.minusDays(days.get(dayOfWeek)).atStartOfDay();
        LocalDateTime lastDayOfWeek = currentDay.plusDays(neededDay).atStartOfDay();

        List<BookedDetail> bookedTicketDetailList = bookedService.getBookedDetailByWeek(firstDayOfWeek, lastDayOfWeek, field_id);

        List<Map<String, Object>> fieldBooked = new ArrayList<>();
        for (BookedDetail btd:bookedTicketDetailList) {
            if(!btd.isCanceled()) {
                Map<String, Object> data = new HashMap<>();
                data.put("field_id", btd.getSoccerField().getId());
                data.put("start", btd.getStartingTime());
                data.put("end", btd.getEndingTime());
                data.put("id", btd.getId().getBookedId());
                if(btd.getBooked().getCustomer() != null) {
                    data.put("phone",
                            btd.getBooked().getCustomer().getAccount().getPhone());
                }
                data.put("total_price", btd.getBooked().getTotalPrice());
                fieldBooked.add(data);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("data", fieldBooked);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/field/cancel/{id}")
    public ResponseEntity<?> cancelOrderField(@PathVariable Long id) {
        if(id == null) {
            return new ResponseEntity<>(new Message("The id must not be null",
                    HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }

        Optional<BookedDetail> optionalBookedDetail = bookedDetailRepository.findByBookedId(id);

        if(!optionalBookedDetail.isPresent() || optionalBookedDetail.isEmpty()) {
            return new ResponseEntity<>(new Message("Cannot find this field",
                    HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }

        BookedDetail bookedDetail = optionalBookedDetail.get();

        LocalDateTime startTime = bookedDetail.getStartingTime();
        boolean isPossibleToCancel =
                startTime.isAfter(ChronoLocalDateTime.from(LocalDateTime.now()).plus(2,
                        ChronoUnit.HOURS));

        System.out.println(isPossibleToCancel);
        System.out.println(ChronoLocalDateTime.from(LocalDateTime.now()).plus(2,
                ChronoUnit.HOURS));
        if(!isPossibleToCancel) {
            return new ResponseEntity<>(new Message("You mustn't cancel " +
                    "order this field before two hours from start time",
                    HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        bookedDetail.setCanceled(true);
        bookedDetailRepository.save(bookedDetail);
        return new ResponseEntity<>(new Message("Cancel order field " +
                "successfully",
                HttpStatus.OK.value()), HttpStatus.OK);
    }


    public Map<Integer, Integer> getDaysOfWeek() {
        Map<Integer, Integer> days = new HashMap<>();
        days.put(DayOfWeek.MONDAY.getValue(), 0);
        days.put(DayOfWeek.TUESDAY.getValue(), 1);
        days.put(DayOfWeek.WEDNESDAY.getValue(), 2);
        days.put(DayOfWeek.THURSDAY.getValue(), 3);
        days.put(DayOfWeek.FRIDAY.getValue(), 4);
        days.put(DayOfWeek.SATURDAY.getValue(), 5);
        days.put(DayOfWeek.SUNDAY.getValue(), 6);
        return days;
    }
}