package com.abui.soccer_system.security;

import com.abui.soccer_system.converter.GenderConverter;
import com.abui.soccer_system.enums.Gender;
import com.abui.soccer_system.model.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Convert;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPrincipal implements UserDetails {
    private Long id;

    private String name;

    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dob;

    private String address;

    private String phone;

    private String email;

    private String username;

    private String password;
    private Collection<? extends SimpleGrantedAuthority> roles;

    public static UserPrincipal build(Account account) {
        List<SimpleGrantedAuthority> authorities =
                account.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().getName())).collect(Collectors.toList());
        return UserPrincipal.builder()
                .id(account.getId())
                .address(account.getAddress())
                .name(account.getName())
                .dob(account.getDob())
                .gender(account.getGender())
                .email(account.getEmail())
                .username(account.getUsername())
                .password(account.getPassword())
                .phone(account.getPhone())
                .roles(authorities)
                .build();
    }

    @Override
    public Collection<? extends SimpleGrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}