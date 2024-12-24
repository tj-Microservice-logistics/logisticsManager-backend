package com.maxrayyy.userservice.dto;

import com.maxrayyy.userservice.model.Users;
import jakarta.validation.constraints.NotBlank;

public class UserDto {

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    private String passwordHash;

    private Users.Role role;

    // Getters 和 Setters
}
