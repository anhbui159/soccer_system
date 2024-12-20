package com.abui.soccer_system.controller;

import com.abui.soccer_system.dto.CustomerDTO;
import com.abui.soccer_system.dto.Message;
import com.abui.soccer_system.enums.RoleEnum;
import com.abui.soccer_system.model.Account;
import com.abui.soccer_system.model.Customer;
import com.abui.soccer_system.security.UserPrincipal;
import com.abui.soccer_system.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customers")
    @ResponseBody
    public ResponseEntity<?> getCustomers(Pageable pageable) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        List<String> authorities =
                userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
        if (authorities.contains(RoleEnum.ADMIN.getName()) || authorities.contains(RoleEnum.EMPLOYEE.getName())) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", HttpStatus.OK.value());
            response.put("data", customerService.findAll(pageable));
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.badRequest().body(new Message("Unauthorized access", HttpStatus.BAD_REQUEST.value()));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable("id") Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Long currentAccountId = userPrincipal.getId();
        Optional<Customer> customerOptional = customerService.findCustomerById(id);
        if (currentAccountId == 1 && customerOptional.isPresent()) {
            return ResponseEntity.ok(customerOptional.get());
        }
        Optional<Customer> customerCurrent =
                customerService.findCustomerByAccountId(currentAccountId);
        List<String> authorities =
                userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
        if (authorities.contains(RoleEnum.ADMIN.getName()) || authorities.contains(RoleEnum.EMPLOYEE.getName())) {
            return ResponseEntity.ok(customerOptional.get());
        }
        if (!customerCurrent.isPresent()) {
            return ResponseEntity.badRequest().body(new Message("Bad request"
                    , HttpStatus.NO_CONTENT.value()));
        }
        Long currentCustomerId = customerCurrent.get().getId();
        if (currentCustomerId == id) {
            return ResponseEntity.ok(customerOptional.get());
        }
        return ResponseEntity.badRequest().body(new Message("Bad request"
                , HttpStatus.NO_CONTENT.value()));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id,
                                            @RequestBody CustomerDTO customerDTO) {
        System.out.println(customerDTO);
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        List<String> authorities =
                userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
        if (!authorities.contains(RoleEnum.ADMIN.getName()) && !authorities.contains(RoleEnum.EMPLOYEE.getName())) {
            return ResponseEntity.badRequest().body(new Message("You don't " +
                    "have permission to access"
                    , HttpStatus.NO_CONTENT.value()));
        }
        Optional<Customer> customerOptional = customerService.findCustomerById(id);
        if (!customerOptional.isPresent()) {
            return ResponseEntity.badRequest().body(new Message("Bad request"
                    , HttpStatus.NO_CONTENT.value()));
        }
        Customer customer = customerOptional.get();
        Account account = customer.getAccount();
        if (customerDTO.getAddress() != null && !customerDTO.getAddress().isEmpty()) {
            account.setAddress(customerDTO.getAddress());
        }

        if (customerDTO.getPhone() != null && !customerDTO.getPhone().isEmpty()) {
            account.setPhone(customerDTO.getPhone());
        }

        if (customerDTO.getEmail() != null && !customerDTO.getEmail().isEmpty()) {
            account.setEmail(customerDTO.getEmail());
        }

        if (customerDTO.getName() != null && !customerDTO.getName().isEmpty()) {
            account.setName(customerDTO.getName());
        }

        if (customerDTO.getDob() != null) {
            account.setDob(customerDTO.getDob());
        }

        if (customerDTO.getGender() != null) {
            account.setGender(customerDTO.getGender());
        }

        if (customerDTO.getRewardPoint() != null) {
            customer.setRewardPoint(customerDTO.getRewardPoint());
        }
        customer.setAccount(account);
        customerService.save(customer);
        return ResponseEntity.ok(customerService.findCustomerById(id).get());
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        List<String> authorities =
                userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

        if (!authorities.contains(RoleEnum.ADMIN.getName()) && !authorities.contains(RoleEnum.EMPLOYEE.getName())) {
            return ResponseEntity.badRequest().body(new Message("Unauthorized access", HttpStatus.BAD_REQUEST.value()));
        }

        Optional<Customer> customer = customerService.findCustomerById(id);
        if (!customer.isPresent()) {
            return ResponseEntity.badRequest().body(new Message("Can't find the customer", HttpStatus.BAD_REQUEST.value()));
        }
        customerService.deleteById(id);
        return ResponseEntity.ok().body(new Message("Delete successfully"
                , HttpStatus.OK.value()));
    }
}