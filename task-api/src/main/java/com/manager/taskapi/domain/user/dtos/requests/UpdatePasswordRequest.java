package com.manager.taskapi.domain.user.dtos.requests;

import com.manager.taskapi.validators.password.CheckPasswords;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@CheckPasswords(password = "password", confirmPassword = "confirmPassword")
public class UpdatePasswordRequest extends PasswordRequest {

    @NotNull
    @NotBlank
    private String currentPassword;
}
