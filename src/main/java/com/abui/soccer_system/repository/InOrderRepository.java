package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.InOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InOrderRepository extends JpaRepository<InOrder, Long> {
    List<InOrder> findAll();

    List<InOrder> findInOrdersByEmployee_Id(Long employeeId);
}