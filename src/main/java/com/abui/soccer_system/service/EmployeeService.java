package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Employee;

public interface EmployeeService {
    double getTotalSalaryPerMonth();

    Employee findEmployeeById(Long id);

    Employee deleteById(Long id);

}