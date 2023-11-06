package com.manager.taskapi.domain.auth.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@ToString
public class AuthResponse extends RepresentationModel<AuthResponse> {

    @Schema(description = "Token de acesso")
    private String jwt;

    @Schema(description = "Tipo de token")
    private String tipo;

    @Schema(description = "Token de atualização")
    private String refreshToken;
}
