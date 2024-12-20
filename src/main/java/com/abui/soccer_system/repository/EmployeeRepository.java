package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @PreAuthorize("hasAuthority('ADMIN')")
    Optional<Employee> findEmployeeByAccountId(Long aLong);

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE')")
    Employee findEmployeeByAccountUsername(String username);

//    @PreAuthorize("hasAuthority('ADMIN')")
//    <S extends Employee> S save(S entity);

    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteById(Long aLong);

    @RestResource(path="groups", rel = "employees")
    @PreAuthorize("hasAuthority('ADMIN')")
    Page<Employee> findEmployeesByField (String fieldName, Pageable pageable);

    @PreAuthorize("hasAuthority('ADMIN')")
    @Query("select i from Employee i where i.isDeleted = false")
    Page<Employee> findAllByDeletedIsFalse(Pageable pageable);

    @PreAuthorize("hasAuthority('ADMIN')")
    boolean existsEmployeeByCard(String identityCard);

//    Iterable<Employee> findAll();
}