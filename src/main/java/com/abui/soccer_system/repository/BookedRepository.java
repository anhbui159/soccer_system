package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.Booked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedRepository extends JpaRepository<Booked, Long> {
    List<Booked> findAll();

    List<Booked> findBookedTicketsByCustomer_Id(Long customerId);

    List<Booked> findBookedTicketsByEmployee_Id(Long employeeId);
}