package com.abui.soccer_system.service;

import com.abui.soccer_system.model.Role;
import com.abui.soccer_system.repository.RoleRepository;
import com.abui.soccer_system.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Optional<Role> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}