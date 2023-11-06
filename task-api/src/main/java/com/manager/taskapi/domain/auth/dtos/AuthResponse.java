package com.manager.taskapi.domain.auth.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthResponse extends RepresentationModel<AuthResponse> {

    private String jwt;
    private String type;
    private String refreshToken;
}
