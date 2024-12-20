package com.abui.soccer_system.service;

import com.abui.soccer_system.model.InOrder;

import java.util.List;

public interface InOrderService {

    int getInOrderCountByDay(int day, int month, int year);
    int getInOrderCountByMonth(int month, int year);
    List<InOrder> getInOrdersByQuarter(int quarter, int year);
}