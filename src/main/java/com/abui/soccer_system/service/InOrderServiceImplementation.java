package com.abui.soccer_system.service;

import com.abui.soccer_system.model.InOrder;
import com.abui.soccer_system.repository.InOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InOrderServiceImplementation implements InOrderService {

    private final InOrderRepository inOrderRepository;

    public List<InOrder> getInOrdersByQuarter(int quarter, int year) {
        List<InOrder> InOrderList = inOrderRepository.findAll();

        for (InOrder receipt: InOrderList) {
            if (receipt.getCreatedAt().getYear() != year || receipt.getCreatedAt().get(IsoFields.QUARTER_OF_YEAR) != quarter) {
                InOrderList.remove(receipt);
            }
        }

        return InOrderList;
    }

    @Override
    public int getInOrderCountByMonth(int month, int year) {
        List<InOrder> InOrderList = new ArrayList<>();

        for (InOrder inOrder: inOrderRepository.findAll()) {
            if (inOrder.getCreatedAt().getYear() == year && inOrder.getCreatedAt().getMonthValue() == month) {
                InOrderList.add(inOrder);
            }
        }

        int totalPrice = 0;

        for (InOrder inOrder:InOrderList) {
            totalPrice += inOrder.getTotalPrice();
        }

        return totalPrice;
    }

    @Override
    public int getInOrderCountByDay(int day, int month, int year) {
        List<InOrder> InOrderList = new ArrayList<>();

        for (InOrder inOrder: inOrderRepository.findAll()) {
            if (inOrder.getCreatedAt().getYear() == year && inOrder.getCreatedAt().getMonthValue() == month && inOrder.getCreatedAt().getDayOfMonth() == day) {
                InOrderList.add(inOrder);
            }
        }

        int totalPrice = 0;

        for (InOrder inOrder:InOrderList) {
            totalPrice += inOrder.getTotalPrice();
        }

        return totalPrice;
    }
}