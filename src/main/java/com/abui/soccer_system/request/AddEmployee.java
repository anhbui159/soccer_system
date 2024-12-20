package com.abui.soccer_system.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEmployee extends SignUp{
    @NotNull(message = "Please enter identity card number")
    @NotEmpty(message = "Please enter identity card number")
    @Length(min = 6, max = 10, message = "Please enter valid identity card number")
    private String identityCard;

    private String description;

    @NotNull(message = "Please enter salary")
    private double salary;

    @NotNull(message = "Please choose field ")
    private Long fieldId;
}
