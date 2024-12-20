package com.abui.soccer_system.controller;

import com.abui.soccer_system.model.Booked;
import com.abui.soccer_system.service.BookedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookedController {
    private BookedService bookedService;

    @GetMapping("/booked-price/{year}/{quarter}")
    public ResponseEntity<?> getTotalBookedPriceByQuarter(@PathVariable("year") int year, @PathVariable("quarter") int quarter) {

        List<Booked> bookedList = bookedService.getBookedByQuarter(quarter, year);

        int totalPrice = 0;

        for (Booked booked:bookedList) {
            totalPrice += booked.getTotalPrice();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("total_price", totalPrice);
        response.put("year", year);
        response.put("quarter", quarter);

        return ResponseEntity.ok(response);
    }
}