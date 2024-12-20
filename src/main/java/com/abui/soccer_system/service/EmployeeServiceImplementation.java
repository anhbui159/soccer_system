package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Employee;
import com.abui.soccer_system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public double getTotalSalaryPerMonth() {
        double totalSalary = 0;
        Iterable<Employee> iterable = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        totalSalary = employees.stream().mapToDouble(Employee::getSalary).sum();
        return totalSalary;
    }

    @Override
    public Employee deleteById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent() && !optionalEmployee.isEmpty()) {
            Employee employee = optionalEmployee.get();
            employee.setDeleted(true);
            employeeRepository.save(employee);
        }
        return optionalEmployee.get();

    }

    @Override
    public Employee findEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent() && !optionalEmployee.isEmpty()) {
            Employee employee = optionalEmployee.get();
            return employee;
        }
        return null;
    }
}