package com.abui.soccer_system.controller;

import com.abui.soccer_system.dto.Message;
import com.abui.soccer_system.enums.EquipmentStatus;
import com.abui.soccer_system.enums.OrderStatus;
import com.abui.soccer_system.model.*;
import com.abui.soccer_system.repository.EmployeeRepository;
import com.abui.soccer_system.repository.InOrderDetailRepository;
import com.abui.soccer_system.repository.InOrderRepository;
import com.abui.soccer_system.repository.SourceRepository;
import com.abui.soccer_system.request.InItem;
import com.abui.soccer_system.request.OrderExtra;
import com.abui.soccer_system.security.UserPrincipal;
import com.abui.soccer_system.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InOrderController {

    private final InOrderRepository inOrderRepository;

    private final ItemService itemService;

    private final SourceRepository sourceRepository;

    private final EmployeeRepository employeeRepository;

    private final InOrderDetailRepository inOrderDetailRepository;

    @PostMapping("/item/order-more")
    public ResponseEntity<?> orderMore(@Valid @RequestBody OrderExtra orderExtra) {
        if (!itemService.existsById((orderExtra.getItemID()))) {
            return new ResponseEntity<>(new Message("The source does not exist", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Item item = itemService.findById(orderExtra.getItemID());
        int importPrice = item.getPrice();
        int moreQuantity = orderExtra.getQuantity();
        item.setQuantity(moreQuantity + item.getQuantity());
        if (item.getStatus().equals(EquipmentStatus.UNAVAILABLE)) {
            item.setStatus(EquipmentStatus.AVAILABLE);
        }

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long currentAccountId = userPrincipal.getId();

        Employee employee = null;

        if (employeeRepository.findEmployeeByAccountId(currentAccountId).isPresent()) {
            employee = employeeRepository.findEmployeeByAccountId(currentAccountId).get();
        }

        int totalPrice = importPrice * moreQuantity;
        InOrder inOrder =
                InOrder.builder()
                        .note(orderExtra.getNote())
                        .orderStatus(OrderStatus.PROCESSING)
                        .totalPrice(totalPrice)
                        .employee(employee)
                        .build();

        InOrderDetail inOrderDetail =
                InOrderDetail.builder()
                        .id(InOrderDetailKey.builder().inOrderId(inOrder.getId()).itemId(item.getId()).build())
                        .inOrder(inOrder)
                        .item(item)
                        .deliveryDate(orderExtra.getDelivery_date())
                        .quantity(orderExtra.getQuantity())
                        .build();


        itemService.save(item);
        InOrder inOrderBody = inOrderRepository.save(inOrder);
        inOrderDetailRepository.save(inOrderDetail);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("item_id", item.getId());
        response.put("receipt_id", inOrderBody.getId());
        response.put("total_price", totalPrice);
        response.put("payment_status", OrderStatus.PROCESSING.toString());
        response.put("created_at", inOrderBody.getCreatedAt());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/item/order")
    public ResponseEntity<?> importItems(@Valid @RequestBody InItem inItem) {
        if (!sourceRepository.existsById((inItem.getSource_id()))) {
            return new ResponseEntity<>(new Message("The supplier is not " +
                    "existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        int importPrice = inItem.getInPrice();
        int quantity = inItem.getQuantity();
        int totalPrice = importPrice * quantity;

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long currentAccountId = userPrincipal.getId();

        Employee employee = null;

        if (employeeRepository.findEmployeeByAccountId(currentAccountId).isPresent()) {
            employee = employeeRepository.findEmployeeByAccountId(currentAccountId).get();
        }
        Source source = sourceRepository.findById(inItem.getSource_id()).get();

        Item item = null;

        if (itemService.existsByNameIgnoreCase((inItem.getName()))) {
            item = itemService.findByName(inItem.getName());
            item.setQuantity(item.getQuantity() + inItem.getQuantity());
        } else {
            //build item
            item =
                    Item.builder()
                            .name(inItem.getName())
                            .status(EquipmentStatus.AVAILABLE)
                            .price(inItem.getInPrice())
                            .unit(inItem.getUnit())
                            .image(inItem.getImage())
                            .quantity(inItem.getQuantity())
                            .note(inItem.getNote())
                            .itemCategory(inItem.getCategory())
                            .source(source)
                            .build();
        }

        InOrder inOrder =
                InOrder.builder()
                        .note(inItem.getNote())
                        .orderStatus(OrderStatus.PROCESSING)
                        .totalPrice(totalPrice)
                        .employee(employee)
                        .build();

        InOrderDetail inOrderDetail =
                InOrderDetail.builder()
                        .id(InOrderDetailKey.builder().inOrderId(inOrder.getId()).itemId(item.getId()).build())
                        .inOrder(inOrder)
                        .item(item)
                        .deliveryDate(inItem.getDelivery_date())
                        .quantity(inItem.getQuantity())
                        .build();

        itemService.save(item);
        InOrder inOrderBody = inOrderRepository.save(inOrder);
        inOrderDetailRepository.save(inOrderDetail);

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("item_id", item.getId());
        response.put("receipt_id", inOrderBody.getId());
        response.put("total_price", totalPrice);
        response.put("payment_status", OrderStatus.PROCESSING.toString());
        response.put("created_at", inOrderBody.getCreatedAt());
        return ResponseEntity.ok(response);
    }
}