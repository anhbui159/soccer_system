package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Item;

public interface ItemService {
    Item findById(Long id);
    Item findByName(String name);
    boolean existsById(Long id);
    boolean existsByNameIgnoreCase(String name);
    int getPurchasePriceByDay(int day, int month, int year);
    int getPurchasePriceByMonth(int month, int year);
    int findSellPrice(Long id);
    Item save(Item item);
}