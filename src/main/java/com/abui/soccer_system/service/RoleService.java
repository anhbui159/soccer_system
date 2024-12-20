package com.abui.soccer_system.service;

import com.abui.soccer_system.enums.RoleEnum;
import com.abui.soccer_system.model.Role;
import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(String name);

    Optional<Role> findByName(RoleEnum name);

    Role save(Role role);
}