package com.manager.taskapi.domain.auth.dtos;

import javax.validation.constraints.NotNull;

public record LoginRequest(@NotNull String email,
                           @NotNull String password) {
}

