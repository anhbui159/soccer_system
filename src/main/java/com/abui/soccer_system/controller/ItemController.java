package com.abui.soccer_system.controller;

import com.abui.soccer_system.dto.Message;
import com.abui.soccer_system.enums.EquipmentStatus;
import com.abui.soccer_system.enums.OrderStatus;
import com.abui.soccer_system.model.*;
import com.abui.soccer_system.repository.EmployeeRepository;
import com.abui.soccer_system.repository.ItemRepository;
import com.abui.soccer_system.repository.OutOrderDetailRepository;
import com.abui.soccer_system.repository.OutOrderRepository;
import com.abui.soccer_system.request.ItemOrder;
import com.abui.soccer_system.security.UserPrincipal;
import com.abui.soccer_system.service.CustomerService;
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

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ItemController {

    private final CustomerService customerService;

    private final EmployeeRepository employeeRepository;

    private final ItemRepository itemRepository;

    private final ItemService itemService;

    private final OutOrderDetailRepository outOrderDetailRepository;

    private final OutOrderRepository outOrderRepository;

    @PostMapping("/item/sell")
    public ResponseEntity<?> buyItem(@Valid @RequestBody ItemOrder itemOrder) {
        Customer customer = null;
        if (itemOrder.getPhone() != null) {
            Optional<Customer> optionalCustomer =
                    customerService.findCustomerByPhone(itemOrder.getPhone());
            if (optionalCustomer.isPresent() && !optionalCustomer.isEmpty()) {
                customer = optionalCustomer.get();
            }
        }

        List<HashMap<String, Long>> items = itemOrder.getItems();

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long currentAccountId = userPrincipal.getId();

        Employee employee = null;

        if (employeeRepository.findEmployeeByAccountId(currentAccountId).isPresent()) {
            employee = employeeRepository.findEmployeeByAccountId(currentAccountId).get();
        }

        Customer finalCustomer = customer;
        Employee finalEmployee = employee;
        AtomicInteger totalPrice = new AtomicInteger();
        List<OutOrderDetail> outOrderDetails = new ArrayList<>();
        Map<Item, Integer> itemsBuy = new HashMap<>();


        for (HashMap<String, Long> item : items) {
            Item itemBuy = itemRepository.findItemById(item.get("id"));

            int currentQuantity = itemBuy.getQuantity();
            int quantityBuy = Math.toIntExact(item.get("quantity"));
            if (quantityBuy > currentQuantity) {
                return new ResponseEntity<>(new Message("The quantity is not enough. Please try again!", HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST);
            }
            totalPrice.set(totalPrice.get() + (int) (itemService.findSellPrice(itemBuy.getId()) * item.get(
                    "quantity")));
            if (currentQuantity - quantityBuy == 0) {
                itemBuy.setStatus(EquipmentStatus.UNAVAILABLE);
            }
            itemBuy.setQuantity(currentQuantity - quantityBuy);
            itemsBuy.put(itemBuy, quantityBuy);
        }

        OutOrder outOrder = OutOrder.builder()
                .orderStatus(OrderStatus.PROCESSING)
                .user(finalCustomer)
                .employee(finalEmployee)
                .totalPrice(totalPrice.get())
                .build();

        outOrderRepository.save(outOrder);

        itemsBuy.forEach((itemBuy, quantityBuy) -> {
            OutOrderDetail outOrderDetail = OutOrderDetail.builder()
                    .item(itemBuy)
                    .outOrder(outOrder)
                    .quantity(quantityBuy)
                    .id(OutOrderDetailKey.builder().outOrderId(outOrder.getId()).itemId(itemBuy.getId()).build())
                    .build();
            outOrderDetails.add(outOrderDetail);
            outOrderDetailRepository.save(outOrderDetail);
            itemService.save(itemBuy);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("receipt_id", outOrder.getId());
        response.put("total_price", totalPrice);
        response.put("payment_status", OrderStatus.PROCESSING.toString());
        response.put("created_at", outOrder.getCreatedAt());

        return ResponseEntity.ok(response);
    }
}