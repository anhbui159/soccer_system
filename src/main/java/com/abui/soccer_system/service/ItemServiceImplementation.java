package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Item;
import com.abui.soccer_system.model.OutOrder;
import com.abui.soccer_system.model.Service;
import com.abui.soccer_system.repository.ItemRepository;
import com.abui.soccer_system.repository.OutOrderRepository;
import com.abui.soccer_system.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ItemServiceImplementation implements ItemService {

    private final ItemRepository itemRepository;

    private final ServiceRepository serviceRepository;

    private final OutOrderRepository outOrderRepository;

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Item findById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isEmpty() && !optionalItem.isPresent())
            return null;
        return optionalItem.get();
    }

    @Override
    public boolean existsById(Long id) {
        return itemRepository.existsById(id);
    }

    public boolean existsByNameIgnoreCase(String name) {
        return itemRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public Item findByName(String name) {
        return itemRepository.findByNameIgnoreCase(name);
    }

    @Override
    public int findSellPrice(Long id) {
        List<Service> services = serviceRepository.findAll();
        for (Service s : services) {
            if (s.getItems().stream().filter(i -> i.getId() == id).count() != 0) {
                return s.getPrice();
            }
        }
        return 0;
    }

    @Override
    public int getPurchasePriceByMonth(int month, int year) {
        List<OutOrder> outOrderList = new ArrayList<>();

        for (OutOrder outOrder : outOrderRepository.findAll()) {
            if (outOrder.getCreatedAt().getYear() == year && outOrder.getCreatedAt().getMonthValue() == month) {
                outOrderList.add(outOrder);
            }
        }

        int totalPrice = 0;

        for (OutOrder outOrder : outOrderList) {
            totalPrice += outOrder.getTotalPrice();
        }

        return totalPrice;
    }

    @Override
    public int getPurchasePriceByDay(int day, int month, int year) {
        List<OutOrder> outOrderList = new ArrayList<>();

        for (OutOrder outOrder : outOrderRepository.findAll()) {
            if (outOrder.getCreatedAt().getYear() == year && outOrder.getCreatedAt().getMonthValue() == month && outOrder.getCreatedAt().getDayOfMonth() == day) {
                outOrderList.add(outOrder);
            }
        }

        int totalPrice = 0;

        for (OutOrder outOrder : outOrderList) {
            totalPrice += outOrder.getTotalPrice();
        }

        return totalPrice;
    }
}