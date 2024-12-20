package com.abui.soccer_system.repository;

import com.abui.soccer_system.model.Account;
import com.abui.soccer_system.projection.AccountProjection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(excerptProjection = AccountProjection.class, exported = false)
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByEmail(String email);
    Optional<Account> findAccountByPhone(String phone);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);

}