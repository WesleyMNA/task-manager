package com.manager.taskapi.domain.user.dtos.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRequest {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String email;
}
