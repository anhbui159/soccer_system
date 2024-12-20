package com.abui.soccer_system.repository;

import com.abui.soccer_system.enums.RoleEnum;
import com.abui.soccer_system.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

    Optional<Role> findRoleByName(String name);

}