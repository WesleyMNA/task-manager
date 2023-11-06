package com.manager.taskapi.domain.user.dtos.requests;

import com.manager.taskapi.validators.password.CheckPasswords;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CheckPasswords(password = "password", confirmPassword = "confirmPassword")
public class PasswordRequest {

    @NotNull
    @NotBlank
    private String password;
    @NotNull
    @NotBlank
    private String confirmPassword;
}
