package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.OutOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutOrderRepository extends CrudRepository<OutOrder,
        Long> {
    List<OutOrder> findAll();

    List<OutOrder> findOutOrdersByUser_Id(Long employeeId);

    List<OutOrder> findOutOrdersByEmployee_Id(Long employeeId);
}