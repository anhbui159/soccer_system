package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Account;

import java.util.Optional;
import java.util.List;

public interface AccountService {

    Optional<Account> findAccountByUsername(String username);

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountByPhone(String phone);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Account save(Account account);

    List<Account> findAll();
}