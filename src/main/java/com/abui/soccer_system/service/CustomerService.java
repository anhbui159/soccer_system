package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CustomerService {
    Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByAccountId(Long id);

    Customer findCustomerByUsername(String username);

    Optional<Customer> findCustomerByPhone(String phone);

    boolean existsById(Long id);
    boolean existsByPhoneNumber(String phone);

    Customer deleteById(Long id);
    Customer save(Customer customer);






}