package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.Customer;
import com.abui.soccer_system.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = CustomerProjection.class, exported = false)
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByAccount_Id(Long aLong);

    Optional<Customer>findCustomerByAccount_Username(String username);

    Optional<Customer> findCustomerByAccount_Phone(String phone);

    @Query("select i from Customer i where i.isDeleted = false")
    Page<Customer> findAllByDeletedIsFalse(Pageable pageable);

    boolean existsCustomerByAccount_Phone(String phone);



}