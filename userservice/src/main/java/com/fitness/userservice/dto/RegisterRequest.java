package com.fitness.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
//Added not Blank
public class RegisterRequest {
    @NotBlank(message="Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=6, message="Must be 6 letters")
    private String password;
    private String firstName;
    private String lastName;
}
