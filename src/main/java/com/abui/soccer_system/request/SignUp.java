package com.abui.soccer_system.request;

import com.abui.soccer_system.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SignUp {

    @Getter
    @NotNull(message = "Please enter your username")
    @NotEmpty(message = "Please enter your username")
    private String username;

    @NotNull(message = "Please enter password")
    @NotEmpty(message = "Please enter password")
    @Length(min = 6, max = 32)
    private String password;

    @NotNull(message = "Please enter email")
    @NotEmpty(message = "Please enter email")
    @Email(message = "Please enter valid email", regexp = "^[\\w!#$" +
            "%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;

    @NotNull(message = "Please enter your name")
    @NotEmpty(message = "Please enter your name")
    private String name;

    @NotNull(message = "Please enter sex type")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Please enter birthday")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dob;

    @NotNull(message = "Please enter address")
    @NotEmpty(message = "Please enter address")
    private String address;

    @NotNull(message = "Please enter phone number")
    @NotEmpty(message = "Please enter phone number")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})", message = "Phone number" +
            " is not valid")
    private String phone;

    private String description;
    @Lob
    private String profilePicture;

    private Set<String> roles;

}
