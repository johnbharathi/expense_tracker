package com.expense_tracker.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Usernaem or email is required")
    private String usernameOrEmail;

    @NotBlank(message = "Password is Required")
    private String password;

}
