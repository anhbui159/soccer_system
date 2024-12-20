package com.abui.soccer_system.controller;

import com.abui.soccer_system.dto.Message;
import com.abui.soccer_system.enums.RoleEnum;
import com.abui.soccer_system.model.Account;
import com.abui.soccer_system.model.Employee;
import com.abui.soccer_system.model.Field;
import com.abui.soccer_system.model.Role;
import com.abui.soccer_system.repository.EmployeeRepository;
import com.abui.soccer_system.repository.FieldRepository;
import com.abui.soccer_system.request.AddEmployee;
import com.abui.soccer_system.security.UserPrincipal;
import com.abui.soccer_system.service.AccountService;
import com.abui.soccer_system.service.EmployeeService;
import com.abui.soccer_system.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {
    private final AccountService accountService;

    private final EmployeeRepository employeeRepository;

    private final RoleService roleService;

    private final FieldRepository fieldRepository;

    private final EmployeeService employeeService;

    @PostMapping("/employees/register")
    public ResponseEntity<?> register(@Valid @RequestBody AddEmployee signUpForm) {
        try {
            if (accountService.existsByUsername(signUpForm.getUsername())) {
                return new ResponseEntity<>(new Message("The username is " +
                        "existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            if (accountService.existsByEmail(signUpForm.getEmail())) {
                return new ResponseEntity<>(new Message("The email is " +
                        "existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            if (accountService.existsByPhone(signUpForm.getPhone())) {
                return new ResponseEntity<>(new Message("The phone number is " +
                        "existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            if(employeeRepository.existsEmployeeByCard(signUpForm.getIdentityCard())) {
                return new ResponseEntity<>(new Message("The identity card is" +
                        " existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }

            if(String.valueOf(signUpForm.getSalary()).length() <= 0) {
                return new ResponseEntity<>(new Message("The salary is must " +
                        "not equal or less than zero",	HttpStatus.BAD_REQUEST.value()),
                        HttpStatus.BAD_REQUEST);
            }
            Set<Role> roles = new HashSet<>();
            Optional<Role> employeeRole = roleService.findByName(RoleEnum.EMPLOYEE);
            Optional<Role> userRole = roleService.findByName(RoleEnum.USER);
            roles.add(employeeRole.get());
            roles.add(userRole.get());
            Account account =
                    Account.builder()
                            .email(signUpForm.getEmail())
                            .phone(signUpForm.getPhone())
                            .gender(signUpForm.getGender())
                            .dob(signUpForm.getDob())
                            .address(signUpForm.getAddress())
                            .name(signUpForm.getName())
                            .username(signUpForm.getUsername())
                            .password(signUpForm.getPassword())
                            .build();

            account.setRoles(roles);

            Optional<Field> fieldOptional =
                    fieldRepository.findById(signUpForm.getFieldId());
            Field field = null;

            if(fieldOptional.isPresent() && !fieldOptional.isEmpty()) {
                field = fieldOptional.get();
            }
            else {
                return new ResponseEntity<>(new Message("The field group is " +
                        "not existed", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }

            Employee employee = Employee.builder()
                    .salary(signUpForm.getSalary())
                    .account(account)
                    .field(field)
                    .description(signUpForm.getDescription())
                    .card(signUpForm.getIdentityCard())
                    .build();
            accountService.save(account);
            employeeRepository.save(employee);
            return new ResponseEntity<>(new Message("Created successfully",
                    HttpStatus.OK.value()),
                    HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new Message(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        List<String> authorities =
                userPrincipal.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());

        if (!authorities.contains(RoleEnum.ADMIN.getName())) {
            return ResponseEntity.badRequest().body(new Message("Unauthorized access", HttpStatus.BAD_REQUEST.value()));
        }

        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            return ResponseEntity.badRequest().body(new Message("Can't find the employee", HttpStatus.BAD_REQUEST.value()));
        }
        employeeService.deleteById(id);
        return ResponseEntity.ok().body(new Message("Deleted successfully"
                , HttpStatus.OK.value()));
    }
}