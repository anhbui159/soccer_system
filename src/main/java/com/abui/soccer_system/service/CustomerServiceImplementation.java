package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Customer;
import com.abui.soccer_system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;
    @Value("${spring.data.rest.default-page-size}")
    private int pageSize;

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAllByDeletedIsFalse(pageable);
    }
    @Override
    public Customer findCustomerByUsername(String username) {
        Optional<Customer> optionalCustomer =
                customerRepository.findCustomerByAccount_Username(username);
        if(optionalCustomer.isPresent() && !optionalCustomer.isEmpty()) {
            Customer customer = optionalCustomer.get();
            return customer;
        }
        return null;
    }

    @Override
    public Optional<Customer> findCustomerByAccountId(Long id) {
        return customerRepository.findCustomerByAccount_Id(id);
    }

    @Override
    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> findCustomerByPhone(String phone) {
        return customerRepository.findCustomerByAccount_Phone(phone);
    }

    @Override
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public boolean existsByPhoneNumber(String phone) {
        return customerRepository.existsCustomerByAccount_Phone(phone);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer deleteById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isPresent() && !optionalCustomer.isEmpty()) {
            Customer customer = optionalCustomer.get();
            customer.setDeleted(true);
            customerRepository.save(customer);
        }
        return optionalCustomer.get();
    }





}