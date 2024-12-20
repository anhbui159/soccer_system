package com.abui.soccer_system.controller;

import com.abui.soccer_system.dto.JwtResponse;
import com.abui.soccer_system.dto.Message;
import com.abui.soccer_system.enums.RoleEnum;
import com.abui.soccer_system.model.Account;
import com.abui.soccer_system.model.Customer;
import com.abui.soccer_system.model.Employee;
import com.abui.soccer_system.model.Role;
import com.abui.soccer_system.repository.EmployeeRepository;
import com.abui.soccer_system.request.SignIn;
import com.abui.soccer_system.request.SignUp;
import com.abui.soccer_system.security.JwtUtils;
import com.abui.soccer_system.security.UserPrincipal;
import com.abui.soccer_system.service.AccountService;
import com.abui.soccer_system.service.CustomerService;
import com.abui.soccer_system.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;

    private final RoleService roleService;

    private final CustomerService customerService;

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUp signUp) {
        try {
            if (accountService.existsByUsername(signUp.getUsername())) {
                return new ResponseEntity<>(new Message("The username already existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            if (accountService.existsByEmail(signUp.getEmail())) {
                return new ResponseEntity<>(new Message("The email existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            if (accountService.existsByPhone(signUp.getPhone())) {
                return new ResponseEntity<>(new Message("The phone number is " +
                        "existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            Set<Role> roles = new HashSet<>();
            Optional<Role> userRole = roleService.findByName(RoleEnum.USER);
            roles.add(userRole.get());
            Account account =
                    Account.builder()
                            .email(signUp.getEmail())
                            .phone(signUp.getPhone())
                            .gender(signUp.getGender())
                            .dob(signUp.getDob())
                            .address(signUp.getAddress())
                            .name(signUp.getName())
                            .username(signUp.getUsername())
                            .password(signUp.getPassword())
                            .build();

            account.setRoles(roles);
            Customer customer = Customer.builder().rewardPoint(0L).account(account).build();
            accountService.save(account);
            customerService.save(customer);
            return new ResponseEntity<>(new Message("Create success",
                    HttpStatus.OK.value()),
                    HttpStatus.OK);
        } catch (Exception e) {
//            e.printStackTrace();
            return new ResponseEntity<>(new Message(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody SignIn signIn) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getUsername(),
                signIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<String> roles =
                userPrincipal.getRoles().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

        if(roles.contains(RoleEnum.EMPLOYEE.getName()) && !roles.contains(RoleEnum.ADMIN.getName()) ) {
            Employee employee =
                    employeeRepository.findEmployeeByAccountUsername(userPrincipal.getUsername());
            if(employee.isDeleted()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.UNAUTHORIZED.value());
                response.put("message", "Account has been deleted. Please contact " +
                        "with " +
                        "administrator to resolve problem");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }

        if(roles.contains(RoleEnum.USER.getName()) && !roles.contains(RoleEnum.ADMIN.getName())) {
            Customer customer =
                    customerService.findCustomerByUsername(userPrincipal.getUsername());
            if(customer.isDeleted()) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.UNAUTHORIZED.value());
                response.put("message", "Account has been deleted. Please contact with administrator to resolve problem");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }

        String token = jwtUtils.createToken(authentication);
        return ResponseEntity.ok(JwtResponse.builder().token(token).name(userPrincipal.getName()).roles(roles).type("Bearer").status(HttpStatus.OK.value()).build());
    }

    @GetMapping("/user-profile")
    public ResponseEntity<?> profile() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Optional<Account> account =
                accountService.findAccountByUsername(userPrincipal.getUsername());
        Account accountResponse = null;
        if(account.isPresent() && !account.isEmpty()) {
            accountResponse = account.get();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("account", accountResponse);
        return ResponseEntity.ok(response);
    }
}