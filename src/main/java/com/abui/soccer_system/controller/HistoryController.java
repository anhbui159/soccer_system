package com.abui.soccer_system.controller;

import com.abui.soccer_system.dto.Message;
import com.abui.soccer_system.dto.OrderHistoryDTO;
import com.abui.soccer_system.dto.TransactionDTO;
import com.abui.soccer_system.model.Booked;
import com.abui.soccer_system.model.InOrder;
import com.abui.soccer_system.model.OutOrder;
import com.abui.soccer_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "/api", produces = "application/hal+json")
@RequiredArgsConstructor
public class HistoryController {

    private final CustomerRepository customerRepository;

    private final EmployeeRepository employeeRepository;

    private final BookedRepository bookedRepository;

    private final OutOrderRepository outOrderRepository;

    private final InOrderRepository inOrderRepository;

    @GetMapping("/history-transaction/customer/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    public ResponseEntity<?> getTransactionCustomer(@PathVariable("id") Long id, Pageable pageable) {
        if (!customerRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("The customer does not exist", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        List<Booked> booked =
                bookedRepository.findBookedTicketsByCustomer_Id(id);

        List<OutOrder> outOrders =
                outOrderRepository.findOutOrdersByUser_Id(id);

        List<TransactionDTO> transactions = new ArrayList<>();

        booked.forEach(bookedTicket -> {
            OrderHistoryDTO bookedDTO =
                    OrderHistoryDTO.builder()
                            .totalPrice(bookedTicket.getTotalPrice())
                            .note(bookedTicket.getNote())
                            .orderStatus(bookedTicket.getStatus())
                            .build();
            transactions.add(TransactionDTO.builder().booked(bookedDTO).createdAt(bookedTicket.getCreatedAt()).type("BOOKED_RECEIPT").build());
        });

        outOrders.forEach(outOrder -> {
            OrderHistoryDTO outOrderDTO = OrderHistoryDTO.builder()
                    .orderStatus(outOrder.getOrderStatus())
                    .note(outOrder.getNote())
                    .totalPrice(outOrder.getTotalPrice())
                    .build();
            transactions.add(TransactionDTO.builder().outOrder(outOrderDTO).createdAt(outOrder.getCreatedAt()).type("SERVICE_RECEIPT").build());
        });

        transactions.sort(Comparator.comparing(TransactionDTO::getCreatedAt).reversed());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), transactions.size());
        List<TransactionDTO> subList = start >= end ? new ArrayList<>() : transactions.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("data", new PageImpl<>(subList, pageable, transactions.size()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history-transaction/employee/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> getTransactionEmployee(@PathVariable("id") Long id, Pageable pageable) {
        if (!employeeRepository.existsById(id)) {
            return new ResponseEntity<>(new Message("The employee's id is " +
                    "existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        List<InOrder> inOrders =
                inOrderRepository.findInOrdersByEmployee_Id(id);

        List<Booked> booked =
                bookedRepository.findBookedTicketsByEmployee_Id(id);

        List<OutOrder> outOrders =
                outOrderRepository.findOutOrdersByEmployee_Id(id);


        List<TransactionDTO> transactions = new ArrayList<>();

        booked.forEach(ticket -> {
            OrderHistoryDTO bookedDTO =
                    OrderHistoryDTO.builder()
                            .totalPrice(ticket.getTotalPrice())
                            .note(ticket.getNote())
                            .orderStatus(ticket.getStatus())
                            .build();
            transactions.add(TransactionDTO.builder().booked(bookedDTO).createdAt(ticket.getCreatedAt()).type("BOOKED_RECEIPT").build());
        });

        outOrders.forEach(outOrder -> {
            OrderHistoryDTO outOrderDTO = OrderHistoryDTO.builder()
                    .orderStatus(outOrder.getOrderStatus())
                    .note(outOrder.getNote())
                    .totalPrice(outOrder.getTotalPrice())
                    .build();
            transactions.add(TransactionDTO.builder().outOrder(outOrderDTO).createdAt(outOrder.getCreatedAt()).type("SERVICE_RECEIPT").build());
        });


        inOrders.forEach(inOrder -> {
            OrderHistoryDTO inOrderDTO = OrderHistoryDTO.builder()
                    .orderStatus(inOrder.getOrderStatus())
                    .note(inOrder.getNote())
                    .totalPrice(inOrder.getTotalPrice())
                    .build();
            transactions.add(TransactionDTO.builder().inOrder(inOrderDTO).createdAt(inOrder.getCreatedAt()).type("IMPORT_RECEIPT").build());
        });

        transactions.sort(Comparator.comparing(TransactionDTO::getCreatedAt).reversed());

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), transactions.size());
        List<TransactionDTO> subList = start >= end ? new ArrayList<>() : transactions.subList(start, end);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("data", new PageImpl<>(subList, pageable, transactions.size()));
        return ResponseEntity.ok(response);
    }
}