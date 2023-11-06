package com.manager.taskapi.domain.user.dtos.requests;

import com.manager.taskapi.validators.notduplicated.NotDuplicated;
import com.manager.taskapi.validators.password.CheckPasswords;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@CheckPasswords(password = "password", confirmPassword = "confirmPassword")
public class UserRequest extends PasswordRequest {

    @NotNull
    @NotBlank
    private String name;
    @NotDuplicated(jpql = """
            SELECT
                COUNT(u.id)
            FROM
                User u
            WHERE
                u.email = :value
            """)
    @Email
    @NotNull
    @NotBlank
    private String email;

    public UserRequest(@NotNull @NotBlank String password,
                       @NotNull @NotBlank String confirmPassword,
                       String name,
                       String email) {
        super(password, confirmPassword);
        this.name = name;
        this.email = email;
    }
}
